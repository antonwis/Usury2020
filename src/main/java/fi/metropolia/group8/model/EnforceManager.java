package fi.metropolia.group8.model;

public class EnforceManager {

    LoanCalculator loanCalculator;

    // TODO replace flavor text prints with event log method calls.

    public EnforceManager() {
        loanCalculator = new LoanCalculator();
    }


    public void Threaten(Loan loan) {
        Victim victim = loan.getVictim();
        VictimTraits trait = VictimTraits.valueOf(victim.getTrait());

        switch (trait) {
            case NORMIE:
                System.out.println("Threatening proved successful. Victim agreed to pay back 15% more");
                float newInterest = loan.getInterest() + 15;
                loan.setInterest(newInterest);
                DataModel.getInstance().saveLoanData(loan);
                break;
            case SCARED:
                System.out.println("Scared victim agreed to pay you back tomorrow.");
                loan.setDueDate(DataModel.getInstance().getCurrentUser().getCurrentDate().plusDays(1));
                DataModel.getInstance().saveLoanData(loan);
                break;
            case JUNKIE:
                System.out.println("Victim was too high to even notice you.");
                break;
            case VIOLENT:
                System.out.println("Victim violently drove you away.");
                break;
            default:
                System.out.println("You threaten the debtor with violence. They agree to pay you back sooner.");
                loan.setDueDate(loan.getDueDate().minusDays(1));
                DataModel.getInstance().saveLoanData(loan);
                break;
        }
    }

    public void Extort(Loan loan) {
        Victim victim = loan.getVictim();
        VictimTraits trait = VictimTraits.valueOf(victim.getTrait());

        switch (trait){
            case SCARED:
                System.out.println("Victim was so scared they paid you back right on the spot.");
                loanCalculator.completeLoan(DataModel.getInstance().getCurrentAlias(), loan);
                break;
            default:
                System.out.println("After blackmailing the debtor they instantly pay you back half the remaining loan.");
                float newValue = (loan.getValue() / 2);
                loan.setValue(newValue);
                Alias alias = loan.getOwner();
                float newSum = alias.getEquity() + loanCalculator.getLoanTotalSum(loan);
                alias.setEquity(newSum);
                DataModel.getInstance().saveLoanData(loan);
                DataModel.getInstance().saveAliasData(alias);
                break;
        }

    }

    public void Torture(Loan loan) {
        Victim victim = loan.getVictim();
        VictimTraits trait = VictimTraits.valueOf(victim.getTrait());

        switch (trait){
            case SCARED:
                System.out.println("Victim didn't survive. You manage to retrieve your initial investment.");
                loanCalculator.repoLoan(DataModel.getInstance().getCurrentAlias(), loan);
                victim.setAlive(false);
                break;
            default:
                System.out.println("You torture the poor debtor. They agree to pay an extra 20% over the original sum.");
                float newValue = (loan.getValue() * 1.2f);
                loan.setValue(newValue);
                DataModel.getInstance().saveLoanData(loan);
                break;
        }

    }

    public void Assassinate(Loan loan) {
        Victim victim = loan.getVictim();
        VictimTraits trait = VictimTraits.valueOf(victim.getTrait());

        switch (trait) {
            case SNEAKY:
                System.out.println("Victim was too sneaky. He got away. Your money was lost.");
                loanCalculator.forfeitLoan(DataModel.getInstance().getCurrentAlias(), loan);
                break;
            case VIOLENT:
                System.out.println("Victim fought back. Your money was lost.");
                loanCalculator.forfeitLoan(DataModel.getInstance().getCurrentAlias(), loan);
                victim.setAlive(false);
                DataModel.getInstance().saveVictimData(victim);
                break;
            default:
                System.out.println("Victim was murdered. Your money was retrieved.");
                loanCalculator.repoLoan(DataModel.getInstance().getCurrentAlias(), loan);
                victim.setAlive(false);
                DataModel.getInstance().saveVictimData(victim);
                break;
        }

    }
}
