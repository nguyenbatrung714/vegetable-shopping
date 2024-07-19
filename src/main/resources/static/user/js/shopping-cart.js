function displayCart() {
    let cart = JSON.parse(localStorage.getItem('cart')) || [];
    if (cart.length === 0) {
        $('#list-product-shopping-cart').append(`
            <h3>Your cart is empty</h3>
        `)
    }
    $('#list-product-shopping-cart').empty()
    cart.forEach(item => {
        $('#list-product-shopping-cart').append(`
            <tr>
                <td class="shoping__cart__item">
                    <img src="${item.imagePath}" alt="">
                    <h5>${item.productName}</h5>
                </td>
                <td class="shoping__cart__price">
                    ${item.price}
                </td>
                <td class="shoping__cart__quantity">
                    <div class="quantity">
                        <div class="pro-qty">
                            <input type="text" value="${item.quantityCart}">
                        </div>
                    </div>
                </td>
                <td class="shoping__cart__total">
                    ${item.price * item.quantityCart}
                </td>
                <td class="shoping__cart__item__close">
                    <span class="icon_close" data-product-id="${item.productId}"></span>
                </td>
            </tr>
        `)
    })
}

// clear cart
$(document).on('click', '#clear-cart', function () {
    swal({
        title: "Are you sure?",
        text: "Once deleted, you will not be able to recover this imaginary file!",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    })
        .then((willDelete) => {
            if (willDelete) {
                localStorage.removeItem('cart');
                displayCart();
                totalCart();
                swal("Poof! Your imaginary file has been deleted!", {
                    icon: "success",
                });
            }
        });
})

// delete one product
$(document).on('click', '.icon_close', function () {
    let cart = JSON.parse(localStorage.getItem('cart')) || [];
    let productId = $(this).data('product-id');
    cart = cart.filter(item => item.productId !== productId)
    localStorage.setItem('cart', JSON.stringify(cart));
    displayCart();
    totalCart();
    swal("delete successfully");
})

$(window).on('load', function () {
    displayCart();
    totalCart()
})
