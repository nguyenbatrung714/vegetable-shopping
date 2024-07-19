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

// total cart
function totalCart() {
    let cart = JSON.parse(localStorage.getItem('cart')) || [];
    let totalCart = 0;
    let totalQuantity = 0;
    cart.forEach(item => {
        totalCart += item.price * item.quantityCart;
        totalQuantity += item.quantityCart;
    });
    $('.total-cart').text(totalCart.toFixed(2));
    $('.total-quantity').text(totalQuantity);
}

$(window).on('load', function () {
    totalCart();
    loadCategoriesToHeader(0, 10, 'asc');
})
