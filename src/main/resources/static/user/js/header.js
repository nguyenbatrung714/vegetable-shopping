function loadCategoriesToHeader(page, size, sort) {
    axios.get(`http://localhost:8080/api/v1/categories?page=${page}&size=${size}&sort=${sort}`)
        .then(function (response) {
            let categories = response.data.content;
            categories.forEach(category => {
                $('.list-categories-header').append(`
                    <li><a href="/${category.categoryId}">${category.categoryName}</a></li>
                `)
            })
        })
        .catch(function (error) {
            console.log(error);
        });
}

window.loadCategoriesToHeader(0, 10, 'asc');
