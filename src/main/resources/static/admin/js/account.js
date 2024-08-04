function loadAccount(page, size) {
    axios.get(`http://localhost:8080/api/v1/users?page=${page}&size=${size}`)
        .then(function (response) {
            let accounts = response.data.content;

            // Xóa nội dung hiện tại của bảng
            $('#account-table').empty();

            // Duyệt qua từng tài khoản và tạo HTML cho từng hàng
            accounts.forEach(account => {
                let row = `
                    <tr>
                        <td>${account.userId}</td>
                        <td>${account.username}</td>
                        <td>${account.email}</td>
                        <td>${account.phoneNumber}</td>
                        <td>${account.address}</td>
                        <td>${account.gender}</td>
                        <td>${account.active}</td>
                        <td>
                            <button class="btn btn-warning account-edit" data-account-id="${account.userId}">Edit</button>
                        </td>
                    </tr>
                `;

                // Thêm hàng vào bảng
                $('#account-table').append(row);
            });
        })
        .catch(function (error) {
        });
}

// Hiển thị thông tin tài khoản lên form khi nhấn nút Edit
$(document).on('click', '.account-edit', function () {
    let userId = $(this).data('account-id');

    axios.get(`http://localhost:8080/api/v1/users/${userId}`)
        .then(function (response) {
            let account = response.data;

            $('#user-id').val(account.userId);
            $('#full-name').val(account.fullname);
            $('#phone-number').val(account.phoneNumber);
            $('#email').val(account.email);
            $('#day-of-birth').val(account.dayOfBirth);
            $('#user-address').val(account.address);
            $('#user-username').val(account.username);

            // Set gender
            $(`input[name="gender"][value="${account.gender}"]`).prop('checked', true);
            $(`input[name="active"][value="${account.active}"]`).prop('checked', true);

            if (account.roles.includes('ROLE_ADMIN')) {
                $('#admin-yes').prop('checked', true);
                $('#admin-no').prop('checked', false);
            } else {
                $('#admin-yes').prop('checked', false);
                $('#admin-no').prop('checked', true);
            }

            $('#accountModal').modal('show');
        })
        .catch(function (error) {
            console.error('Error fetching account details:', error);
        });
});

// update
$(document).on('click', '#update-account', function () {

    let userData = {
        fullname: $('#full-name').val(),
        email: $('#email').val(),
        phoneNumber: $('#phone-number').val(),
        dayOfBirth: $('#day-of-birth').val(),
        address: $('#user-address').val(),
        username: $('#user-username').val(),
        gender: $('input[name="gender"]:checked').val(),
        active: $('input[name="active"]:checked').val() === 'true',
        roles: [$('input[name="role"]:checked').val()]
    };

    axios.put(`http://localhost:8080/api/v1/auth/${$('#user-id').val()}`, userData)
        .then(response => {
            Swal.fire('Success', 'User updated successfully', 'success');
            resetForm();
            loadAccount(0, 20);
            $('#accountModal').modal('hide');
        })
        .catch(error => {
            Swal.fire('Error', 'Failed to update user', 'error');
        });

});

function resetForm() {
    $('#user-id').val('');
    $('#full-name').val('');
    $('#phone-number').val('');
    $('#email').val('');
    $('#day-of-birth').val('');
    $('#user-address').val('');
    $('#user-username').val('');

    $('input[name="gender"]').prop('checked', false);
    $('#gender-male').prop('checked', true);

    $('input[name="active"]').prop('checked', false);
    $('#active-yes').prop('checked', true);

    $('input[name="role"]').prop('checked', false);
    $('#admin-yes').prop('checked', true);
}

$(document).on('click', '#reset-account', function () {
    resetForm();
});

$('#save-account').on('click', function () {
    let userData = {
        fullname: $('#full-name').val(),
        password: $('#password').val(),
        email: $('#email').val(),
        phoneNumber: $('#phone-number').val(),
        dayOfBirth: $('#day-of-birth').val(),
        address: $('#user-address').val(),
        username: $('#user-username').val(),
        gender: $('input[name="gender"]:checked').val(),
        active: $('input[name="active"]:checked').val(),
        roles: [$('input[name="role"]:checked').val()]
    };

    axios.post('http://localhost:8080/api/v1/auth', userData)
        .then(function (response) {
            Swal.fire('Success', 'User save successfully', 'success');
            $('#accountModal').modal('hide');
            loadAccount(0, 20);
        })
        .catch(function (error) {
            Swal.fire('Error', 'Failed to save user', 'error');
            console.log('Error adding user:', error);
        });
});

$(document).ready(function() {
    loadAccount(0, 20);
});
