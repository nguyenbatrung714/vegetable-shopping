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
                               <i class="fa-solid fa-minus" data-product-id="${item.productId}"></i>
                                <input type="text" value="${item.quantityCart}" readonly>
                                <i class="fa-solid fa-plus" data-product-id="${item.productId}"></i>
                            </div>
                    </div>
                </td>
                <td class="shoping__cart__total">
                    ${(item.price * item.quantityCart).toFixed(2)}
                </td>
                <td class="shoping__cart__item__close">
                    <span class="icon_close" data-product-id="${item.productId}"></span>
                </td>
            </tr>
        `)
    })
}

// Tăng số lượng sản phẩm
$(document).on('click', '.fa-plus', function () {
    let cart = JSON.parse(localStorage.getItem('cart')) || [];
    let productId = $(this).data('product-id');
    cart = cart.map(item => {
        if (item.productId === productId) {
            item.quantityCart += 1;
        }
        return item;
    });
    localStorage.setItem('cart', JSON.stringify(cart));
    totalCart();
    displayCart();
});

// Giảm số lượng sản phẩm
$(document).on('click', '.fa-minus', function () {
    let cart = JSON.parse(localStorage.getItem('cart')) || [];
    let productId = $(this).data('product-id');
    cart = cart.map(item => {
        if (item.productId === productId && item.quantityCart > 1) {
            item.quantityCart -= 1;
        }
        return item;
    });
    localStorage.setItem('cart', JSON.stringify(cart));
    totalCart();
    displayCart();
});

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
})

$(window).on('load', function () {
    displayCart();
    totalCart()
})
