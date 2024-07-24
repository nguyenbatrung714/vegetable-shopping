function checkLogin1() {
    let checkValue = sessionStorage.getItem('token');
    $('#userMenuContent').empty();
    if (checkValue == null) {
        $('#userMenuContent').append(`
                <li><a href="/vegetable-shopping/login">Login</a></li>
                <li><a href="/vegetable-shopping/register">Register</a></li>
        `)
    } else {
        $('#userMenuContent').append(`
                <li><a href="/vegetable-shopping/orders">Your orders</a></li>
                <li><a href="/vegetable-shopping/update-account">Update account</a></li>
                <li><a href="/vegetable-shopping/change-password">Change password</a></li>
                <li><a onclick="logout()">Log out</a></li>
        `)
    }
}

function logout() {
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('account');
    window.location.href = "/vegetable-shopping/home";
}

window.checkLogin1();