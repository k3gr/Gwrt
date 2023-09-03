$(document).ready(function () {
    $('#last-race').click(function () {
        $('#last-race').addClass('active')
        $('#main-scoreboard').removeClass('active')
        $('#main-body').addClass('active')
        $('#main-head').addClass('active')
        $('#last-body').removeClass('active')
        $('#last-head').removeClass('active')
    })
})
$(document).ready(function () {
    $('#main-scoreboard').click(function () {
        $('#main-scoreboard').addClass('active')
        $('#last-race').removeClass('active')
        $('#main-body').removeClass('active')
        $('#main-head').removeClass('active')
        $('#last-body').addClass('active')
        $('#last-head').addClass('active')
    })

})
$('input').on('focus',function (){
    $(this).parent().find('label').addClass('active');
})
$('input').on('focusout',function (){
    if(!this.value){
        $(this).parent().find('label').removeClass('active');
    }
})