function quitarCreditCardFicticia() {
	var holderName = document.getElementById("holderName");
	var brandName = document.getElementById("brandName");
	var number = document.getElementById("number");
	var expirationMonth = document.getElementById("expirationMonth");
	var expirationYear = document.getElementById("expirationYear");
	var CVV = document.getElementById("CVV");

	if (holderName.value == "FICTICIA") {
		if (brandName.value == "FICTICIA") {
			if (number.value == "0000000000000000") {
				if (expirationMonth.value == 1) {
					if (expirationYear.value == 10) {
						if (CVV.value == 100) {
							holderName.value = "";
							brandName.value = "";
							number.value = "";
							expirationMonth.value = "0";
							expirationYear.value = "0";
							CVV.value = "0";
						}
					}
				}
			}
		}
	}
};