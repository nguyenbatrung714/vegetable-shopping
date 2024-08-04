async function changePassword() {
    try {
        let account = JSON.parse(sessionStorage.getItem('account'));
        let userId = account.userId;

        let formData = new FormData();

        let oldPassword = document.getElementById('old-user-password').value;
        let newPassword = document.getElementById('user-password').value;

        formData.append('oldPassword', oldPassword);
        formData.append('newPassword', newPassword);

        await axios.put(`http://localhost:8080/api/v1/auth/${userId}/password`,formData, {
            headers: {
                "Content-Type": "application/json"
            }
        } )
        Swal.fire({
            title: 'Change password',
            text: 'Change password successfully',
            icon: 'success',
            button: 'OK'
        });
        setTimeout(function () {
            window.location.href = "/vegetable-shopping/home";
        }, 1000);
    } catch (err) {
        Swal.fire({
            title: 'Change password Failed',
            text: 'change password failed',
            icon: 'error',
            button: 'OK'
        });
    }
}

$(document).on('click', '#change-password', function () {
    changePassword();
})
