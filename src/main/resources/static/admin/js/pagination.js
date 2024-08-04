function setupPagination(totalPages, size, loadFunction) {
    $('.first-page').off('click').on('click', function () {
        currentPage = 0;
        loadFunction(currentPage, size);
    });

    $('.next-page').off('click').on('click', function () {
        if (currentPage < totalPages - 1) {
            currentPage += 1;
            loadFunction(currentPage, size);
        }
    });

    $('.prev-page').off('click').on('click', function () {
        if (currentPage > 0) {
            currentPage -= 1;
            loadFunction(currentPage, size);
        }
    });

    $('.last-page').off('click').on('click', function () {
        currentPage = totalPages - 1;
        loadFunction(currentPage, size);
    });
}

