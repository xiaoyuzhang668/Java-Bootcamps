$(document).ready(function () {
    loadItems();
});

//getAll() - java display all
function loadItems() {
    $('#itemRows').empty();
    $('#errorMessages').empty();
    $('#change').val('');
    var itemRows = $('#itemRows');

    $.ajax({
        type: 'GET',
        url: 'http://vending.us-east-1.elasticbeanstalk.com/items',
        success: function (itemArray) {
            $.each(itemArray, function (index, item) {
                var name = item.name;
                var price = item.price;
                var quantity = item.quantity;
                var itemId = item.id;

                //loop to get and display item
                var row = '<div onclick="selectItem(' + itemId + ')" class="item col-md-6 col-lg-4 mt-3"><div class="card position-relative shadow-sm" style = "height:220px;">';
                row += '<p class = "itemId position-absolute top-0 start-0 mt-2 ps-2">' + itemId + '</p>';
                row += '<h6 class="mt-5">' + name + '</h6>';
                row += '<h5 class="card-title mt-3">$' + price + '</h5>';
                row += '<p class="card-text mt-auto pb-2">Quantity Left:  ' + quantity + '</p>';
                row += ' </div></div>';

                itemRows.append(row);
            })

        },
        error: function () {
            $('#errorMessages')
                .append($('<li>')
                    .attr({
                        class: 'list-group-item list-group-item-danger'
                    })
                    .text('Error calling web service to display the items. Please try again later.'));
        }
    })
}

//add dollar
function addDollarButton() {
    $('#deposit').val((Number($("#deposit").val()) + 1.00).toFixed(2));
    clearMessage();
}

//add quarter
function addQuarterButton() {
    $('#deposit').val((Number($("#deposit").val()) + 0.25).toFixed(2));
    clearMessage();
}

//add dime
function addDimeButton() {
    $('#deposit').val((Number($("#deposit").val()) + 0.10).toFixed(2));
    clearMessage();
}

//add nickel
function addNickelButton() {
    $('#deposit').val((Number($("#deposit").val()) + 0.05).toFixed(2));
    clearMessage();
}

function clearMessage() {
    $('#message').val('');
    $('#errorMessages').empty();
    $('#change').val('');
}

//click on specific item selection - in the loadItems function
function selectItem(itemId) {
    clearMessage()
    $('#addItem').val(itemId);
}

//make purchase button is clicked
function makePurchaseButton() {
    let haveValidationErrors = checkAndDisplayValidationErrors($('#purchaseItem').find('input'));
    let message = document.getElementById("message");

    clearMessage();

    if (haveValidationErrors) {
        return false;
    }

    $.ajax({
        type: 'POST',
        url: 'http://vending.us-east-1.elasticbeanstalk.com/money/' + $('#deposit').val() + '/item/' + $('#addItem').val(),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'dataType': 'json',
        success: function (data, status) {

            message.value = "Thank You!!!";
            $('#deposit').val('0.00');
            $('#addItem').val('');
            loadItems();
            $("#change").val(displayChange(data.quarters, data.dimes, data.nickels, data.pennies));
        },
        error: function (result, status) {
            if (result.status == 422) {
                message.value = result.responseJSON.message;
            } else {
                $('#errorMessages')
                    .append($('<li>')
                        .attr({
                            class: 'list-group-item list-group-item-danger'
                        })
                        .text('Error calling web service. Please try again later.'));
            }

        }
    })
}

function displayChange(quarter, dime, nickel, penny) {
    let message = '';

    let arr = [quarter, dime, nickel, penny];
    let arrValue = ["Quarter: ", "Dime: ", "Nickel: ", "Penny: "];
    for (let i = 0; i < arr.length; i++) {
        message += arrValue[i] + arr[i] + " ";
    }
    return message;
}

function calculateChange() {
    let deposit = ((document.getElementById('deposit').value) * 100).toFixed(2);

    let quarter = Math.floor(deposit / 25);
    let dime = Math.floor(deposit % 25 / 10);
    let nickel = Math.floor(deposit % 25 % 10 / 5);
    let penny = Math.floor(deposit % 25 % 10 % 5);

    return displayChange(quarter, dime, nickel, penny);
}


function returnChangeButton() {
    if ($('#deposit').val() != '0.00') {
        $('#change').val(calculateChange());
        $('#deposit').val('0.00');
        $('#addItem').val('');
        $('#message').val('');
        $('#errorMessages').empty();
    } else {
        $('#addItem').val('');
        clearMessage();
    }
}

function checkAndDisplayValidationErrors(input) {
    $('#errorMessages').empty();

    var errorMessages = [];

    input.each(function () {
        if (!this.validity.valid) {
            var errorField = $('label[for=' + this.id + ']').text();
            errorMessages.push(errorField + ' ' + this.validationMessage);
        }
    });

    if (errorMessages.length > 0) {
        $.each(errorMessages, function (index, message) {
            $('#errorMessages').append($('<li>').attr({
                class: 'list-group-item list-group-item-danger'
            }).text(message));
        });
        // return true, indicating that there were errors
        return true;
    } else {
        // return false, indicating that there were no errors
        return false;
    }
}
