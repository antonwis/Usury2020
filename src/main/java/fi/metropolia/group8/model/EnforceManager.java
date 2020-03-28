package fi.metropolia.group8.model;

public class EnforceManager {

    LoanCalculator loanCalculator;

    // TODO replace flavor text prints with event log method calls.

    public EnforceManager() {
        loanCalculator = new LoanCalculator();
    }


    public void Threaten(Victim victim) {

    }

    public void Extort(Victim victim) {

    }

    public void Torture(Victim victim) {

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
