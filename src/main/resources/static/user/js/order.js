function loadOrderByUserId(page, size) {
    let userId = 1;
    axios.get(`http://localhost:8080/api/v1/orders/${userId}/orders?page=${page}&size=${size}`)
        .then(function (response) {
            let orders = response.data.content;
            orders.forEach(item => {
                let paymentMethodDisplay = item.paymentMethod ? 'Payment on delivery' : 'Oline payment';
                let paymentStatusDisplay = item.paymentMethod ? 'Unpaid' : 'Paid';
                $('#orders-by-user-id').append(`
                    <tr>
                        <td>${item.orderId}</td>
                        <td>${item.orderStatus}</td>
                        <td>${item.addressShipping}</td>
                        <td>${paymentMethodDisplay}</td>
                        <td>${paymentStatusDisplay}</td>
                        <td>${item.totalAmount}<small class="mx-1 font-weight-bold">VNĐ</small></td>
                        <td class="text-red">
                            <i data-order-id="${item.orderId}" class="fa-regular fa-eye view-order-details"></i>
                        </td>
                    </tr>
                `)
            })
        })
        .catch(function (error) {
            console.log(error);
        })
}

$(document).on('click', '.view-order-details', async function () {
    let orderId = $(this).data('order-id');

    try {
        let response = await axios.get(`http://localhost:8080/api/v1/order-details/${orderId}/order`);
        let orderDetails = response.data;

        let tableHtml = `
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Product Name</th>
                        <th>Quantity</th>
                        <th>Price</th>
                    </tr>
                </thead>
                <tbody>
        `;

        for (let item of orderDetails) {
            try {
                let productResponse = await axios.get(`http://localhost:8080/api/v1/products/${item.productId}`);
                let productName = productResponse.data.productName;

                tableHtml += `
                    <tr>
                        <td>${productName}</td>
                        <td>${item.quantity}</td>
                        <td>${item.price}</td>
                    </tr>
                `;
            } catch (error) {
                console.log(error);
            }
        }

        tableHtml += `
                </tbody>
            </table>
        `;

        Swal.fire({
            title: 'Cart Details',
            html: tableHtml,
            icon: 'info',
            width: '50%',
            button: 'OK'
        });
    } catch (error) {
        console.log('Error fetching order details:', error);
    }
});

window.loadOrderByUserId(0, 10);
