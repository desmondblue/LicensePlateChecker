package com.topdesk;

public class MyLicensePlateChecker implements ILicensePlateChecker {
    private LicensePlateSpecification specification;

    @Override
    public void setSpecification(LicensePlateSpecification specification) {
        this.specification = specification;
    }

    @Override
    public boolean isValid(LicensePlate licensePlate) {
        if (validateLicensePlateData(licensePlate)) {
            return false;
        } else {
            // Validate town code
            boolean checkTownCode = this.specification.getValidTownCodes().contains(licensePlate.getTownCode());
            // Validate license plate letters length
            boolean checkLetterLength = this.specification.getMinNumLetters() <= licensePlate.getLetterSequence().length();
            // Validate license plate digits length
            boolean checkDigitLength = this.specification.getMinNumDigits() <= licensePlate.getDigitSequence().length();
            // Validate licence plate number total length
            String licensePlateNumber = licensePlate.getTownCode() + licensePlate.getLetterSequence() + licensePlate.getDigitSequence();
            boolean checkTotalLength = this.specification.getTotalLength() == licensePlateNumber.length();
            return checkTownCode && checkLetterLength && checkDigitLength && checkTotalLength;
        }
    }

    private boolean validateLicensePlateData(LicensePlate licensePlate) {
        boolean isNotValid = this.specification == null ||
                    licensePlate == null || licensePlate.getDigitSequence() == null ||
                    licensePlate.getLetterSequence() == null || licensePlate.getTownCode() == null
                    || !licensePlate.getLetterSequence().matches("[A-Z]+")
                    || !licensePlate.getDigitSequence().matches("[0-9]+");
        return isNotValid;
    }
}
