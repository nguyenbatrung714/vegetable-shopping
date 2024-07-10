
function loadCategoriesToAside(page, size, sort) {
    axios.get(`http://localhost:8080/api/v1/categories?page=${page}&size=${size}&sort=${sort}`)
        .then(function (response) {
            let categories = response.data.content;
            categories.forEach(category => {
                $('#list-categories-aside').append(`
                    <li><a href="/${category.categoryId}">${category.categoryName}</a></li>
                `)
            })
        })
        .catch(function (error) {
            console.log(error);
        });
}

function loadProductToShopGrid(page, size, sort) {
    axios.get(`http://localhost:8080/api/v1/products?page=${page}&size=${size}&sort=${sort}`)
        .then(function (response) {
            let products = response.data.content;
            // Clear old products
            $('#list-products-shop-grid').empty();

            products.forEach(product => {
                $('#list-products-shop-grid').append(`
                <div class="col-lg-4 col-md-6 col-sm-6">
                    <div class="product__item">
                        <div class="product__item__pic set-bg" style="background-image: url(${product.imagePath})">
                            <ul class="product__item__pic__hover">
                                <li><a href="#"><i class="fa fa-heart"></i></a></li>
                                <li><a href="#"><i class="fa fa-retweet"></i></a></li>
                                <li><a href="#"><i class="fa fa-shopping-cart"></i></a></li>
                            </ul>
                        </div>
                        <div class="product__item__text">
                            <h6><a href="#">${product.productName}</a></h6>
                            <h5>$ ${product.price}</h5>
                        </div>
                    </div>
                </div>
                `);
            })


        })
        .catch(function (error) {
            console.log(error);
        })
}

$(document).ready(function () {
    $('#sort-product-shop-grid').change(function () {
        let sortBy = $('#sort-product-shop-grid').val();
        loadProductToShopGrid(0, 9, sortBy);
    });
})

window.loadCategoriesToAside(0, 10, 'asc')
window.loadProductToShopGrid(0, 9, 'asc')
