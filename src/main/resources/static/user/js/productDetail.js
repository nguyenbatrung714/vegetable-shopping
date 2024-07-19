let id = location.pathname.split('/')[4];

function loadProductDetail() {
    axios.get(`http://localhost:8080/api/v1/products/${id}`)
        .then(function (response) {
            let product = response.data;
            $('#product-detail').append(`
            <div class="col-lg-6 col-md-6">
                <div class="product__details__pic">
                    <div class="product__details__pic__item">
                        <img class="product__details__pic__item--large"
                             src="${product.imagePath}" alt="">
                    </div>
                    <div class="product__details__pic__slider owl-carousel">
                        <img data-imgbigurl="img/product/details/product-details-2.jpg"
                             src="img/product/details/thumb-1.jpg" alt="">
                        <img data-imgbigurl="img/product/details/product-details-3.jpg"
                             src="img/product/details/thumb-2.jpg" alt="">
                        <img data-imgbigurl="img/product/details/product-details-5.jpg"
                             src="img/product/details/thumb-3.jpg" alt="">
                        <img data-imgbigurl="img/product/details/product-details-4.jpg"
                             src="img/product/details/thumb-4.jpg" alt="">
                    </div>
                </div>
            </div>
            <div class="col-lg-6 col-md-6">
                <div class="product__details__text">
                    <h3>${product.productName}</h3>
                    <div class="product__details__price">$ ${product.price}</div>
                    <p>${product.description}</p>
                    <div class="product__details__quantity">
                        <div class="quantity">
                            <div class="pro-qty">
                                <input type="text" value="1">
                            </div>
                        </div>
                    </div>
                    <a href="#" class="primary-btn">ADD TO CARD</a>
                    <ul>
                        <li><b>Availability</b> <span>${product.quantity} In Stock</span></li>
                        <li><b>Shipping</b> <span>01 day shipping. <samp>Free pickup today</samp></span></li>
                        <li><b>Weight</b> <span>${product.weight} kg</span></li>
                        <li><b>Share on</b>
                            <div class="share">
                                <a href="#"><i class="fa-brands fa-facebook"></i></i></a>
                                <a href="#"><i class="fa-brands fa-square-twitter"></i></i></a>
                                <a href="#"><i class="fa-brands fa-instagram"></i></i></a>
                                <a href="#"><i class="fa-brands fa-pinterest"></i></a>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            `)
            loadProductsRelated(product.categoryId);
        })
        .catch(function (error) {
            console.log('error: ' + error);
        })
}

function loadProductsRelated(categoryId) {
    axios.get(`http://localhost:8080/api/v1/products/category/${categoryId}`)
        .then(function (response) {
            let items = response.data.content;
            items.forEach(item => {
                $('#related-product').append(`
                    <div class="col-lg-3 col-md-4 col-sm-6">
                        <div class="product__item">
                            <div class="product__item__pic set-bg"
                             style="background-image: url(${item.imagePath});">
                                <ul class="product__item__pic__hover">
                                    <li><a href="#"><i class="fa fa-retweet"></i></a></li>
                                    <li><a href="#"><i class="fa fa-shopping-cart"></i></a></li>
                                </ul>
                            </div>
                            <div class="product__item__text">
                                <h6><a href="#">${item.productName}</a></h6>
                                <h5>$ ${item.price}</h5>
                            </div>
                        </div>
                    </div>
                `)}
            )
        })
        .catch(function (err) {
            console.log(err);
        })
}

$(window).on('load', function () {
    loadProductDetail()
})