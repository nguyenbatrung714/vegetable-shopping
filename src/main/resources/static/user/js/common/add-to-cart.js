$(document).on('click', '.add-to-cart', function (event) {
    let product = JSON.parse($(this).attr('data-product'));
    console.log(product);
    let cart = JSON.parse(localStorage.getItem('cart')) || [];

    const existingProduct = cart.findIndex(item => item.productId === product.productId);
    if (existingProduct >= 0) {
        cart[existingProduct].quantityCart += 1;
    } else {
        product.quantityCart = 1;
        cart.push(product);
    }

    localStorage.setItem('cart', JSON.stringify(cart));
    totalCart();
});
