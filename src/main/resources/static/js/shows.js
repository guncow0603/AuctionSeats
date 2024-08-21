var curCategoryName = null;
var cursorId = null;
var index = 0;
var loading = false;

function getShowsCategories() {
    return new Promise(function (resolve, reject) {
        $.ajax({
            type: "GET",
            url: `${getUrl()}/api/v1/shows-categorys`,
            success: function (response) {
                for (let i = 0; i < response.data.length; i++) {
                    let name = response.data[i].categoryName;

                    let div = $('<div>').addClass("btn-div")
                    let btn = $('<button>').text(name)
                        .addClass("btn round-btn")
                        .addClass(response.data[i].categoryName);

                    if (curCategoryName === null && i === 0) {
                        btn.addClass("active");
                        curCategoryName = response.data[i].categoryName;
                    }

                    btn.on("click", function () {
                        $(".btn-div button").removeClass("active");
                        $(`.${name}`).addClass("active");

                        curCategoryName = response.data[i].categoryName;
                        cursorId = null;
                        $(".shows-posters-row").empty();

                        loadShows();
                    });

                    let db = div.append(btn);
                    $(".btn-by-category").append(db);
                }
                resolve(response.data);
            },
            error: function (qXHR, textStatus) {
                reject(qXHR);
                console.log(qXHR);
            }
        });
    });
}


function loadShows() {
    if (cursorId === -1 || loading) {
        return;
    }

    loading = true;
    var apiUrl = cursorId === null
        ? `${getUrl()}/api/v1/shows?categoryName=${curCategoryName}&size=20`
        : `${getUrl()}/api/v1/shows?cursorId=${cursorId}&categoryName=${curCategoryName}&size=20`;

    $.ajax({
        type: "GET",
        url: apiUrl,
        success: function (response) {
            displayPosters(response.data);
        },
        complete: function () {
            loading = false;  // 호출이 완료되면 loading을 false로 설정
        }
    });
}

function displayPosters(data) {
    var div = $(".shows-posters-row")
    var row = $('<div>').addClass("d-flex justify-content-start posters-div row shows-posters");

    data.showsResponses.forEach(function (shows) {
        var row_div = $('<div>')
            .addClass("col-md-2 poster")
            .append($('<img>').attr("src", `${shows.s3Url}`).addClass("shows-poster-img"))
            .append($('<p>').text(shows.title.split(" - ")[0]).addClass("shows-title")
            );

        row_div.on("click", function () {
            redirectToPageWithParameter(
                "/shows/shows-details.html",
                "showsId",
                encode(shows.showsId)
            );
        });

        row.append(row_div);
        index += 1;
    });
    div.append(row);
    cursorId = data.nextCursorId;
}
