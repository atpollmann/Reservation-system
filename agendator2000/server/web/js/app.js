function padInteger(x, length) {
    x = ''+x;
    while(x.length < length) {
        x = '0'+x;
    }
    return x;
}
function date_str(dateObj) {
    var yyyy = dateObj.year().toString();
    var MM = (dateObj.month()+1).toString(); // getMonth() is zero-based
    var dd  = dateObj.date().toString();
    return padInteger(yyyy, 4)  + '-' + padInteger(MM, 2) + '-' + padInteger(dd, 2);
}

function datetime_str(dateObj) {
    var yyyy = dateObj.year().toString();
    var MM = (dateObj.month()+1).toString(); // getMonth() is zero-based
    var dd  = dateObj.date().toString();
    var hh = dateObj.hour().toString();
    var mm = dateObj.minute().toString();
    var ss = dateObj.second().toString();
    return padInteger(yyyy, 4)  + '-' + padInteger(MM, 2) + '-' + padInteger(dd, 2) + 'T' + padInteger(hh, 2) + ':' + padInteger(mm, 2) + ':' + padInteger(ss, 2);
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