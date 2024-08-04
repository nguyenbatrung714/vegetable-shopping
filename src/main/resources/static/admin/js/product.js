let currentPage = 0;

function loadProductsToTable(page, size) {
    axios.get(`http://localhost:8080/api/v1/products?page=${page}&size=${size}`)
        .then(function (response) {
            let products = response.data.content;
            $('#products-table').empty();
            products.forEach(
                product => {
                    $('#products-table').append(`
                        <tr>
                            <td>${product.productId}</td>
                            <td>${product.productName}</td>
                            <td><img src="${product.imagePath}"></td>
                            <td>${product.quantity}</td>
                            <td>${product.price}</td>
                            <td>${product.weight}</td>
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

            // pagination
            let totalPages = response.data.totalPages;
            setupPagination(totalPages, size, loadProductsToTable);
        })
        .catch(function (error) {
            console.log(error);
        })
}

//click edit
$(document).on('click', '.product-edit-table', function () {

    let productId = $(this).data('product-id');

    axios.get(`http://localhost:8080/api/v1/products/${productId}`)
        .then(function (response) {
            let product = response.data;

            $('#product-category-id').val(product.categoryId);
            $('#product-id').val(product.productId);
            $('#product-name').val(product.productName);
            $('#product-quantity').val(product.quantity);
            $('#product-price').val(product.price);
            $('#product-weight').val(product.weight);
            $('#product-description').val(product.description);

            $('#productModal').modal('show');
        })
        .catch(function (error) {
            console.log('Error fetching product details:', error);
        });
});

// update
$(document).on('click', '#update-product', function () {
    let formData = new FormData($('#product-form')[0]);

    formData.append('categoryId', $('#product-category-id').val());
    formData.append('productName', $('#product-name').val());
    formData.append('quantity', $('#product-quantity').val());
    formData.append('price', $('#product-price').val());
    formData.append('weight', $('#product-weight').val());
    formData.append('description', $('#product-description').val());
    let fileInput = $('#product-image')[0].files[0];
    if (fileInput) {
        formData.append('file', fileInput);
    }

    let productId = $('#product-id').val();

    axios.put(`http://localhost:8080/api/v1/products/${productId}`, formData, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    }).then(response => {
        resetFormProduct();
        Swal.fire({icon: 'success', title: 'Product updated successfully!', confirmButtonText: 'OK'});
        loadProductsToTable(0, 10);
    })
        .catch(error => {
            console.log('Error updating product:', error);
            Swal.fire({icon: 'error', title: 'Error!', text: 'Failed to update product.', confirmButtonText: 'OK'});
        });

});

// delete form
$(document).on('click', '#delete-product', function () {

    let productId = $('#product-id').val();

    axios.delete(`http://localhost:8080/api/v1/products/${productId}`)
        .then(response => {
            resetFormProduct();
            Swal.fire({icon: 'success', title: 'Product delete successfully!', confirmButtonText: 'OK'});
            loadProductsToTable(0, 10);
        })
        .catch(error => {
            console.log('Error delete product:', error);
            Swal.fire({icon: 'error', title: 'Error!', text: 'Failed to update product.', confirmButtonText: 'OK'});
        });

});

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
            loadProductsToTable(0, 10);
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

// load categories to input
function loadCategoriesProduct() {
    axios.get('http://localhost:8080/api/v1/categories')
        .then(function (response) {
            let categories = response.data.content;
            let select = $('#product-category-id');
            select.empty();

            select.append('<option value="">Select a category</option>');

            categories.forEach(category => {
                select.append(`<option value="${category.categoryId}">${category.categoryName}</option>`);
            });
        })
        .catch(function (error) {
            console.log('Error loading categories:', error);
        });
}

//Reset form
function resetFormProduct() {
    $('#product-form')[0].reset();
}

$(document).on('click', '#reset-product', function () {
    resetFormProduct();
})

// add product
$(document).on('click', '#add-product', function addProduct() {

    let formData = new FormData($('#product-form')[0]);

    formData.append('categoryId', $('#product-category-id').val());
    formData.append('productName', $('#product-name').val());
    formData.append('quantity', $('#product-quantity').val());
    formData.append('price', $('#product-price').val());
    formData.append('weight', $('#product-weight').val());
    formData.append('description', $('#product-description').val());
    let fileInput = $('#product-image')[0].files[0];
    if (fileInput) {
        formData.append('file', fileInput);
    }

    axios.post('http://localhost:8080/api/v1/products', formData, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
        .then(function (response) {
            Swal.fire({
                icon: 'success',
                title: 'Success!',
                text: 'Product added successfully!',
                confirmButtonText: 'OK'
            });
            resetFormProduct();
            loadProductsToTable(0, 10);
        })
        .catch(function (error) {
            console.log('Error adding product:', error);
            Swal.fire({
                icon: 'error',
                title: 'Error!',
                text: 'Failed to add product.',
                confirmButtonText: 'OK'
            });
        });
})

$(window).on('load', function () {
    loadProductsToTable(0, 10);
    loadCategoriesProduct();
});
