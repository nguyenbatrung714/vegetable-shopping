let token;

function loadProductsToTable() {
    axios.get('http://localhost:8080/api/v1/products')
        .then(function (response) {
            let products = response.data.content;
            $('#products-table').empty();
            products.forEach(
                product => {
                    $('#products-table').append(`
                        <tr>
                            <td>${product.productId}</td>
                            <td>${product.productName}</td>
                            <td><img src="${product.imagePath}" alt="img"></td>
                            <td>${product.quantity}</td>
                            <td>${product.price}</td>
                            <td>${product.weight}</td>
                            <td>...</td>
                            <td>
                                <button class="btn btn-warning product-edit-table" data-product-id="${product.productId}">
                                    <i class="fa-regular fa-pen-to-square"></i>
                                </button>
                                <button class="btn btn-danger product-delete-table" data-product-id="${product.productId}">
                                    <i class="fa-regular fa-trash-can"></i>
                                </button>
                            </td>
                        </tr>
                    `)
                }
            )
        })
        .catch(function (error) {
            console.log(error);
        })
}

// delete product
$(document).on('click', '.product-delete-table', function () {
    let productId = $(this).data('product-id');
    axios.delete(`http://localhost:8080/api/v1/products/${productId}`)
        .then(function (response) {
            Swal.fire({
                title: 'Product',
                text: 'Delete product successfully',
                icon: 'success',
                button: "OK"
            });
        })
        .catch(function (error) {
            console.log(error);
            Swal.fire({
                title: 'Product',
                text: error.data,
                icon: 'warning',
                button: "OK"
            });
        })
})

// edit product
$(document).on('click', ('.product-edit-table'), function () {
    let productId = $(this).data('product-id');
    axios.get(`http://localhost:8080/api/v1/products/${productId}`, {
        headers: {
            Authorization: 'Bearer ' + token
        }
    })
        .then(function (response) {
            console.log(response.data);
        })
        .catch(function (error) {
            console.log(error);
        })
})

$(window).on('load', function () {
    loadProductsToTable();
});