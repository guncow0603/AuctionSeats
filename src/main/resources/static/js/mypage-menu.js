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
