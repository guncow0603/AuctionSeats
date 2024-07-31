function getShowsCategories() {
    $.ajax({
        type: "GET",
        url: getUrl() + `/api/v1/shows-categorys`,
        success: function (response) {
            for (let i = 0; i < response.data.length; i++) {
                let name = response.data[i].categoryName;
                if (name === "서커스마술") name = "서커스/마술";
                let a = $('<a>').text(name)
                    .addClass("nav-link link-dark category-a")
                    .on("click", function () {
                        // 카테고리별 공연 리스트 페이지로 이동
                    });
                let li = $('<li>').append(a);
                $(".shows-menu-ul").append(li);
                let div = $('<div>').addClass("btn-div")
                let btn = $('<button>').text(name)
                    .addClass("btn round-btn")
                    .addClass(response.data[i].categoryName);
                if (name === '연극') {
                    btn = $('<button>').text(response.data[i].categoryName)
                        .addClass("btn round-btn active")
                        .addClass(response.data[i].categoryName);
                }
                btn.on("click", function () {
                    clickOnCategoryBtn(response.data[i].categoryName);
                });
                let db = div.append(btn);
                $(".btn-by-category").append(db);
            }
        },
        error: function (qXHR, textStatus) {
            console.log(qXHR);
        }
    });
}
function clickOnCategoryBtn(name) {
    $.ajax({
        type: "GET",
        url: getUrl() + `/api/v1/shows`,
        data: {
            page: 0,
            size: 5,
            sort: "endDate",
            categoryName: name
        },
        success: function (response) {
            $(".btn-div button").removeClass("active");
            $(`.${name}`).addClass("active");
            $("#shows-posters").empty();
            for (let i = 0; i , response.data.showsSlice.content.length; i++) {
                let d = response.data.showsSlice.content[i];

                let eid = encode(d.showsId);
                let pd = $('<div>')
                    .append(
                        $('<img>').attr("src", `${d.s3Url}`).addClass("shows-poster-img")
                            .on("click", function () {
                                redirectToPageWithParameter(
                                    "/shows/shows-details.html",
                                    "showsId",
                                    eid
                                );
                            })
                    )
                    .append($('<p>').text(d.title.split(" - ")[0]).addClass("shows-title"))
                    .addClass("pd");
                $("#shows-posters").append(pd);
            }
        },
        error: function (qXHR, textStatus) {
            console.log(qXHR);
        }
    });
}