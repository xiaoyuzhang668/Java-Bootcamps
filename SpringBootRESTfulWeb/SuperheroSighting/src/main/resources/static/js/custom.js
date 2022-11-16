$(document).ready(function () {});

function clearMessage() {
    $('.alert-danger').addClass('d-none');
}

//================================= POWER =================================
//cancel add power 
function cancelAddPower() {
    $('#namePower').val('');
    $('#descriptionPower').val('');
    clearMessage();
}

//cancel edit power 
function cancelEditPower() {
    location.reload();
}


//================================= ORGANIZATION =================================
//cancel add power 
function cancelAddOrganization() {
    $('#nameOrganization').val('');
    $('#descriptionOrganization').val('');
    $('#addressOrganization').val('');
    $('#contactOrganization').val('');
    $('#phoneOrganization').val('');
    clearMessage();
}

//cancel edit organization 
function cancelEditOrganization() {
    location.reload();
}

//================================= HERO =================================
//cancel add hero 
function cancelAddHero() {
    $('#nameHero').val('');
    $('#descriptionHero').val('');
    $('#hero').checked;
    $('#powerHero').val('');
    $('#organizationHero').val('');
    $('#photo').val('');
}

//cancel edit hero 
function cancelEditHero() {
    location.reload();
}

//================================= LOCATION =================================
//cancel add location 
function cancelAddLocation() {
    $('#nameLocation').val('');
    $('#descriptionLocation').val('');
    $('#addressLocation').val('');
    $('#latitudeLocation').val('');
    $('#longitudeLocation').val('');
    clearMessage();
}

//cancel edit location 
function cancelEditLocation() {
    location.reload();
}

//================================= SIGHTING =================================
//cancel add sighting 
function cancelAddSighting() {
    $('#heroSighting').val('');
    $('#locationSighting').val('');
    $('#sightingDescription').val('');
    $('#sightingDate').val('');
}

//cancel edit sighting 
function cancelEditSighting() {
    location.reload();
}
