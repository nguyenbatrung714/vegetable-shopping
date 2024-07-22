let id = location.pathname.split('/')[4];

function loadBlogDetail(id) {
    axios.get(`http://localhost:8080/api/v1/blogs/${id}`)
        .then(function (response) {
            let blog = response.data;
            $('.blog__details__text').append(`
                <img src="${blog.imageUrl}" style="width: 100%; height: 450px">
                <div>${blog.content}</div>
            `)
        })
        .catch(error => console.log(error))
}

$(window).on('load', function () {
    loadBlogDetail(id);
})
