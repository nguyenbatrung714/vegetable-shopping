function loadProducts() {
    let cart = JSON.parse(localStorage.getItem('cart'));
    let totalCart = 0;
    cart.forEach(item => {
        $('#product-checkout').append(`
            <li>${item.productName}<small>(${item.quantityCart})</small> <span>$ ${item.quantityCart * item.price}</span></li>
        `)
        totalCart += item.price * item.quantityCart;
    })

    let shoppingFee = parseFloat($('#shipping-fee').text());
    let totalOrder = totalCart + shoppingFee;
    $('#total-amount').text(totalOrder.toFixed(2));
}

$(document).on('click', '#order-checkout', function () {

    let paymentMethod = $('input[name="payment-method"]:checked').val();
    let paymentStatus = (paymentMethod === 'true') ? 'false' : 'true';

    let user = JSON.parse(sessionStorage.getItem('account'));
    let userId = user.userId;

    let orderRequest = {
        addressShipping: $('#address-shipping').val(),
        note: $('#note').val(),
        orderStatus: 'WAIT_FOR_CONFIRMATION',
        paymentMethod: paymentMethod,
        paymentStatus: paymentStatus,
        shippingFee: parseFloat($('#shipping-fee').text()),
        totalAmount: parseFloat($('#total-amount').text()),
        userId: userId
    };

    let cart = JSON.parse(localStorage.getItem('cart'));

    let orderDetailRequests = cart.map(item => {
        return {
            productId: item.productId,
            quantity: item.quantityCart,
            price: item.price
        };
    });

    axios.post('http://localhost:8080/api/v1/orders/place', {orderRequest, orderDetailRequests})
        .then(function (response) {
            localStorage.removeItem('cart');
            swal("Place order successfully!");
            setTimeout(function () {
                window.location.href = 'http://localhost:8080/vegetable-shopping/orders';
            }, 1000);
        })
        .catch(function (error) {
            console.log(error);
        });

})

$(window).on('load', function () {
    loadProducts();
})
