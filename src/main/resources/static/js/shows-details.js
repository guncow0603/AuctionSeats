$(document).ready(function () {
    var queryParams = getQueryParams();
    if (queryParams["showsId"]) {
        let id = decode(queryParams["showsId"]);
        fetchShowsInfo(id);
        fetchGradesInfo(id);
        fetchScheduleInfo(id);
    } else {
        // showsId가 없다면 에러 처리
        console.error('No showsId found');
    }
});

var selectScheduleId = -1;

function fetchShowsInfo(showsId) {
    $.ajax({
        url: `${getUrl()}/api/v1/shows/${showsId}`,
        type: 'GET',
        success: function (data) {
            var response = data.data;
            $('#showsTitle').text(response.title);
            $('#main-image').attr('src', response.s3Urls[0]);

            var imagesList = $('#images-container .images-list');
            imagesList.empty(); // 기존 이미지들 제거

            response.s3Urls.forEach(function (url, index) {
                if (index > 0) {
                    imagesList.append($('<img>').attr('src', url).attr('alt', '상세 이미지 ' + index));
                }
            });

            $('#placeFullName').text(response.placeName);
            $('#placeLocationAddress').text(response.placeAddress);
            $('#showsDate').text(response.startDate + ' ~ ' + response.endDate);
            $('#showsTime').text(response.runningTime + '분');
            $('#showsAge').text(response.ageGrade);

            // 여기에 추가적인 처리를 할 수 있습니다.
        },
        error: function (error) {
            console.error('Error fetching shows info:', error);
        }
    });
}

function fetchGradesInfo(showsId) {
    $.ajax({
        url: `${getUrl()}/api/v1/shows/${showsId}/grade`,
        type: 'GET',
        success: function (data) {
            var response = data.data; // 가정: 응답이 { data: List<GradeGetResponse> } 형태라고 가정
            response.forEach(function (grade) {
                $('#grades-table').append($('<tr>')
                    .append($('<td>').text(grade.name))
                    .append($('<td>').text(grade.normalPrice.toLocaleString() + '원')));
            });
        },
        error: function (error) {
            console.error('Error fetching grades info:', error);
        }
    });
}

function fetchScheduleInfo(showsId) {
    $.ajax({
        url: `${getUrl()}/api/v1/shows/${showsId}/schedules`,
        type: 'GET',
        success: function (data) {
            var events = data.data.map(function (schedule) {
                return {
                    title: schedule.sequence + '차',
                    start: new Date(schedule.startDateTime),
                    id: schedule.scheduleId
                };
            });
            initCalendar(events);
        },
        error: function (error) {
            console.error('Error fetching schedule info:', error);
        }
    });
}

function initCalendar(events) {
    $('#calendar').fullCalendar({
        defaultView: 'month',
        header: {
            left: 'prev',
            center: 'title',
            right: 'next'
        },
        events: events,
        eventClick: function (calEvent) {
            $('#selected-scheduled-fix').text(calEvent.title);
            selectScheduleId = calEvent.id;
        }
    });
}

$('#book-btn').click(function () {
    if (selectScheduleId !== -1) {
        if (!isLogined()) {
            errorAlert("로그인이 필요합니다.");
            redirectToPage("/login.html");
            return;
        }

        const queryParams = getQueryParams();
        const showsId = decode(queryParams["showsId"]);
        const paramValueMap = {
            showsId: showsId,
            scheduleId: selectScheduleId
        };
        redirectToPageWithParameters('/reservation/shows-reserve.html', paramValueMap);
    } else {
        errorAlert('선택한 회차가 없습니다');
    }
});

function showGrades() {
    $('#grades-list').toggle(); // show와 hide를 toggle로 변경
}
