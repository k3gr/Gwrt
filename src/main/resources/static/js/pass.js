function checkPassword(fieldConfirmPassword){
    if(fieldConfirmPassword.value !== $("#password").val()){
        fieldConfirmPassword.setCustomValidity("Hasła nie pasują do siebie.");
    }else{
        fieldConfirmPassword.setCustomValidity("")
    }
}