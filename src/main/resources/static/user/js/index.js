function loadSectionCategories() {
    axios.get('http://localhost:8080/api/v1/categories')
        .then(function (response) {
            let categories = response.data.content;
            categories.forEach(
                category => {
                    $('.categories__slider').owlCarousel('add',
                        `<div class="col-lg-3">
                        <div class="categories__item set-bg" 
                        data-setbg="${category.imagePath}" 
                        style="background-image: url(${category.imagePath})"
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
                                        <li><a href="#"><i class="fa fa-heart"></i></a></li>
                                        <li><a href="#"><i class="fa fa-retweet"></i></a></li>
                                        <li><a href="#"><i class="fa fa-shopping-cart"></i></a></li>
                                    </ul>
                                </div>
                                <div class="featured__item__text">
                                    <h6><a href="#">${product.productName}</a></h6>
                                    <h5>$ ${product.price}</h5>
                                </div>
                            </div>
                        </div>
                    `)
                }
            )
        })
        .catch(function (error) {
            console.log(error);
        })
}

window.loadSectionCategories();
window.loadFeatureProduct(0, 8);

