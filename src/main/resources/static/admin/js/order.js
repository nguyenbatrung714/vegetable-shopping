function loadAllOrderToTable(page, size) {
    axios.get(`http://localhost:8080/api/v1/orders?page=${page}&size=${size}`)
        .then(function (response) {
            let orders = response.data.content;
            $('#order-table').empty();
            orders.forEach(order => {
                let paymentMethodDisplay = order.paymentMethod ? 'Payment on delivery' : 'Online payment';
                let paymentStatusDisplay = order.paymentMethod ? 'Unpaid' : 'Paid';
                $('#order-table').append(`
                    <tr class="odd">
                        <td>${order.orderId}</td>
                        <td>${order.createdDate}</td>
                        <td>${paymentMethodDisplay}</td>
                        <td>${paymentStatusDisplay}</td>
                        <td>${order.orderStatus}</td>
                        <td>${order.addressShipping}</td>
                        <td>${order.shippingFee}</td>
                        <td>${order.totalAmount}</td>
                        <td>
                            <a data-order-id="${order.orderId}" class="me-3 text-primary mx-1 order-show" data-bs-toggle="modal" data-bs-target="#orderModal">
                            <i class="fa-solid fa-pencil"></i></a>
                        </td>
                    </tr>
                `);
            });

            // pagination
            let totalPages = response.totalPages;
            setupPagination(totalPages, size, loadAllOrderToTable);
        })
        .catch(function (err) {
            console.log(err.message);
        });
}
$(document).on('click', '[data-bs-target="#orderModal"]', async function () {
    let orderId = $(this).data('order-id');

    try {
        // Gọi API để lấy thông tin đơn hàng
        let { data: order } = await axios.get(`http://localhost:8080/api/v1/orders/${orderId}`);
        $('#order-id').val(order.orderId);
        $('#order-date').val(order.createdDate);
        $('#order-status').val(order.orderStatus);
        $('#address-shipping').val(order.addressShipping);
        $('#shipping-fee').val(order.shippingFee);
        $('#order-total').val(order.totalAmount);
        $('#order-note').val(order.note);

        // Gọi API để lấy chi tiết đơn hàng
        let { data: response } = await axios.get(`http://localhost:8080/api/v1/order-details/${orderId}/order`);

        let tableContent = `
            <table class="table table-bordered table-hover">
                <thead>
                    <tr>
                        <th>STT</th>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Total Price</th>
                    </tr>
                </thead>
                <tbody>
        `;

        response.forEach((item, index) => {
            tableContent += `
                <tr>
                    <td>${index + 1}</td>
                    <td>${item.productName}</td>
                    <td>${item.price}</td>
                    <td>${item.quantity}</td>
                    <td>${item.price * item.quantity}</td>
                </tr>
            `;
        });

        tableContent += `
                </tbody>
            </table>
        `;

        $('#show-order-details').html(tableContent);
    } catch (error) {
        console.log('Error fetching order details:', error);
    }
});

// update
$(document).on('click', '#order-update', function () {
    let orderId = $('#order-id').val();
    let order = {
        orderStatus: $('#order-status').val()
    }

    axios.put(`http://localhost:8080/api/v1/orders/${orderId}`, order)
        .then(function (response) {
            Swal.fire({
                    title: 'Order',
                    text: 'update order successfully',
                    icon: 'success',
                    button: "OK"
            })
            loadAllOrderToTable(0, 20);
        })
        .catch(function (error) {
            console.log(error.message);
            Swal.fire({
                title: 'Order',
                text: 'update order failed',
                icon: 'warning',
                button: "OK"
            })
        })
})

$(window).on('load', function () {
    loadAllOrderToTable(0, 20);
});
