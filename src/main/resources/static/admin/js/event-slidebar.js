function showContent(contentId) {
    const contents = document.querySelectorAll('.content');
    contents.forEach(content => content.style.display = 'none');
    document.getElementById(contentId).style.display = 'block';

    // Remove active class from all nav-links
    const navLinks = document.querySelectorAll('.nav-link');
    navLinks.forEach(link => link.classList.remove('active'));

    // Add active class to clicked nav-link
    event.currentTarget.classList.add('active');
}
