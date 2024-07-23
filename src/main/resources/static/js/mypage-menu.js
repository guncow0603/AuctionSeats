function getMyInfo(token, id) {
    $.ajax({
        type: "GET",
        url: `/api/v1/users/${id}`,
        headers: {
            "Authorization": token
        },
        success: function (data) {
            let info = data.data;
            $("#info-email").text(info.email);
            $("#info-name").text(info.name);
            $("#info-nickname").text(info.nickname);
            $("#info-birth").text(info.birth);
            $("#info-phone").text(info.phoneNumber);
        },
        error: function (jqXHR, textStatus) {
            console.log(jqXHR);
            console.error(textStatus);
        },
    })
}

function updateUserInfo(token, id) {
    let nickname = $("#update-nickname").val();
    let phoneNumber = $("#update-phone").val();

    $.ajax({
        type: "PUT",
        url: `/api/v1/users/${id}`,
        contentType: "application/json",
        headers: {
            "Authorization": token
        },
        data: JSON.stringify({
            nickname: nickname,
            phoneNumber: phoneNumber
        }),
        success: function (data) {
            alert("회원 정보를 수정했습니다.")
            movePageWithToken(`/user-info.html`);
        },
        error: function (jqXHR, textStatus) {
            resetValidationMessages();
            let response = jqXHR.responseJSON;
            if (response) {
                if (response.code.substring(2, 4) === "04") {
                    $("#update-nickname-span").text(response.message);
                }
                if (response.code.substring(2, 4) === "06") {
                    $("#update-phoneNumber-span").text(response.message);
                }
            }
        }
    });
}

function getPointChargeList(token, page) {
    $.ajax({
        type: "GET",
        url: `/api/v1/points/charge?page=${page}`,
        contentType: "application/json",
        headers: {
            "Authorization": token
        },
        data: {
            "page": page
        },
        success: function (data) {
            $(".list-tb-body").empty();
            $(".pagination").empty();

            if (data.code === "P00000" && data.data.content) {
                displayData(data.data);
            }
        },
        error: function (jqXHR, textStatus) {
            console.log(jqXHR);
            console.log(textStatus);
        },
    });
}

function displayData(data) {
    let size = data.pageable.pageSize;
    let curIndex = data.number;
    for (let i = 0; i < data.content.length; i++) {
        let id = $('<td>').text(size * curIndex + (i + 1));
        let orderId = $('<td>').text(data.content[i].orderId);
        let date = $('<td>').text(data.content[i].time);
        let amount = $('<td>').text(data.content[i].amount.toLocaleString() + "원");

        let tr = $('<tr>').append(id).append(orderId).append(date).append(amount);
        $(".list-tb-body").append(tr);
    }

    let totalPage = data.totalPages;
    for (let i = 0; i < totalPage; i++) {
        const pageNumber = i + 1;
        let link = $('<a>');
        link.addClass('list-a');
        link.href = '#';
        link.text(pageNumber);

        if (i === data.number) {
            link.addClass('now');
        }

        link.on("click", function () {
            reissueToken((token => {
                    getPointChargeList(token, i);
                }
            ));
        });

        $(".pagination").append(link);

    }
}
