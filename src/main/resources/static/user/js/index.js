function loadSectionCategories() {
    axios.get('http://localhost:8080/api/v1/categories')
        .then(function (response) {
            let categories = response.data.content;
            categories.forEach(
                category => {
                    $('.categories__slider').owlCarousel('add',
                        `<div class="col-lg-3">
                        <div class="categories__item set-bg" 
                        style="background-image: url(${category.imagePath});">
                            <h5><a href="#">${category.categoryName}</a></h5>
                        </div>
                      </div>`).owlCarousel('update');
                });
        })
        .catch(function (error) {
            console.log(error);
        })
}

function loadFeatureProduct(page, size) {
    axios.get(`http://localhost:8080/api/v1/products?page=${page}&size=${size}`)
        .then(function (response) {
            let products = response.data.content;
            products.forEach(
                product => {
                    $('.featured__filter').append(`
                        <div class="col-lg-3 col-md-4 col-sm-6 mix fastfood vegetables">
                            <div class="featured__item">
                                <div class="featured__item__pic set-bg" style="background-image: url(${product.imagePath})">
                                    <ul class="featured__item__pic__hover">
                                        <li><a><i class="fa fa-retweet"></i></a></li>
                                        <li><a class="add-to-cart" data-product='${JSON.stringify(product)}'>
                                        <i class="fa fa-shopping-cart"></i></a></li>
                                    </ul>
                                </div>
                                <div class="featured__item__text">
                                    <h6><a href="/vegetable-shopping/products/product-detail/${product.productId}">${product.productName}</a></h6>
                                    <h5>$ ${product.price}</h5>
                                </div>
                            </div>
                        </div>
                    `);
                }
            );
        })
        .catch(function (error) {
            console.log(error);
        });
}

function loadThreeBlogs(page, size) {
    axios.get(`http://localhost:8080/api/v1/blogs?page=${page}&size=${size}`)
        .then(function (response) {
            let blogs = response.data.content;
            blogs.forEach(
                blog => {
                    $('#blog-index').append(`
                        <div class="col-lg-4 col-md-4 col-sm-6">
                            <div class="blog__item">
                                <div class="blog__item__pic">
                                    <img src="${blog.imageUrl}" alt="" style="width: 370px; height: 265px;">
                                </div>
                                <div class="blog__item__text">
                                    <ul>
                                        <li><i class="fa fa-calendar-o"></i> ${blog.createdDate}</li>
                                    </ul>
                                    <h5><a href="/vegetable-shopping/blogs/blog-detail/${blog.blogId}">${blog.title}</a></h5>
                                    <p>Sed quia non numquam modi tempora indunt ut labore et dolore magnam aliquam quaerat </p>
                                </div>
                            </div>
                        </div>
                    `)
                }
            )
        })
        .catch(error => {
            console.log(error);
        })
}

$(document).on('click', '#button-search-by-name', function () {
    let productName = $('#search-by-name').val();
    axios.get(`http://localhost:8080/api/v1/products/search/by-name`, {
        params: {
            page: 0,
            size: 8,
            productName: productName
        }
    })
        .then(function (response) {
            let products = response.data.content;
            $('.featured__filter').empty();
            products.forEach(
                product => {
                    $('.featured__filter').append(`
                        <div class="col-lg-3 col-md-4 col-sm-6 mix fastfood vegetables">
                            <div class="featured__item">
                                <div class="featured__item__pic set-bg" style="background-image: url(${product.imagePath})">
                                    <ul class="featured__item__pic__hover">
                                        <li><a><i class="fa fa-retweet"></i></a></li>
                                        <li><a class="add-to-cart" data-product='${JSON.stringify(product)}'>
                                        <i class="fa fa-shopping-cart"></i></a></li>
                                    </ul>
                                </div>
                                <div class="featured__item__text">
                                    <h6><a href="/vegetable-shopping/products/product-detail/${product.productId}">${product.productName}</a></h6>
                                    <h5>$ ${product.price}</h5>
                                </div>
                            </div>
                        </div>
                    `);
                }
            );
            $('html, body').animate({
                scrollTop: $('.featured__filter').offset().top
            }, 1000);
        })
        .catch(function (error) {
            console.log(error);
        });
})

$(window).on('load', function () {
    loadSectionCategories();
    loadFeatureProduct(0, 8);
    loadThreeBlogs(0, 3);
})


