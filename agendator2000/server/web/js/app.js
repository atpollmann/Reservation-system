function date_str(dateObj) {
    var yyyy = dateObj.year().toString();
    var mm = dateObj.month().toString(); // getMonth() is zero-based
    var dd  = dateObj.date().toString();
    return yyyy  + '-' + (mm.length>1?mm:"0"+mm[0]) + '-' + (dd.length>1?dd:"0"+dd[0]);
}

function my_indexof(arr, elem, comparator) {
    for(var i = 0; i < arr.length; i++) {
        if (comparator(arr[i])) {
            return i;
        }
    }
    return -1;
}

function my_find(arr, elem, comparator) {
    var i = my_indexof(arr, elem, comparator);
    if (i > -1) {
        return arr[i];
    }
    return null;
}