package fi.metropolia.group8.model;

public class EnforceManager {

    private static EnforceManager instance;

    /**
     * Retrieves the global instance
     * @return returns the singleton instance
     */
    public static EnforceManager getInstance() {
        if(instance == null) {
            instance = new EnforceManager();
        }
        return instance;
    }


    // TODO replace flavor text prints with event log method calls.

    public EnforceManager() {}


    public void Threaten(Loan loan) {
        Victim victim = loan.getVictim();
        VictimTraits trait = VictimTraits.valueOf(victim.getTrait());

        switch (trait) {
            case NORMIE:
                float newInterest = loan.getInterest() + 15;
                loan.setInterest(newInterest);
                DataModel.getInstance().saveLoanData(loan);
                EventManager.getInstance().threatEvent(trait);
                break;
            case SCARED:
                loan.setDueDate(DataModel.getInstance().getCurrentUser().getCurrentDate().plusDays(1));
                DataModel.getInstance().saveLoanData(loan);
                EventManager.getInstance().threatEvent(trait);
                break;
            case JUNKIE:
            case VIOLENT:
                EventManager.getInstance().threatEvent(trait);
                break;
            default:
                EventManager.getInstance().threatEvent(trait);
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
                LoanCalculator.getInstance().completeLoan(loan);
                EventManager.getInstance().extortionEvent(trait);
                break;
            default:
                // Default behavior is to pay back half now
                LoanCalculator.getInstance().payHalfLoan(loan);
                EventManager.getInstance().extortionEvent(trait);
                break;
        }

    }

    public void Torture(Loan loan) {
        Victim victim = loan.getVictim();
        VictimTraits trait = VictimTraits.valueOf(victim.getTrait());
        float modifier = 20f;

        switch (trait){
            case SCARED:
                System.out.println("Victim didn't survive. You manage to retrieve your initial investment.");
                LoanCalculator.getInstance().repoLoan(DataModel.getInstance().getCurrentAlias(), loan);
                victim.setAlive(false);
                EventManager.getInstance().tortureEvent(trait, modifier);
                break;
            default:
                // Default behavior: Modify the loan's original value through a percentage modifier.
                LoanCalculator.getInstance().modifyLoanValue(loan, modifier);
                EventManager.getInstance().tortureEvent(trait, modifier);
                break;
        }

    }

    public void Assassinate(Loan loan) {
        Victim victim = loan.getVictim();
        VictimTraits trait = VictimTraits.valueOf(victim.getTrait());

        switch (trait) {
            case SNEAKY:
                LoanCalculator.getInstance().forfeitLoan(DataModel.getInstance().getCurrentAlias(), loan);
                EventManager.getInstance().assassinationEvent(trait);
                break;
            case VIOLENT:
                LoanCalculator.getInstance().forfeitLoan(DataModel.getInstance().getCurrentAlias(), loan);
                victim.setAlive(false);
                DataModel.getInstance().saveVictimData(victim);
                EventManager.getInstance().assassinationEvent(trait);
                break;
            default:
                LoanCalculator.getInstance().repoLoan(DataModel.getInstance().getCurrentAlias(), loan);
                victim.setAlive(false);
                DataModel.getInstance().saveVictimData(victim);
                EventManager.getInstance().assassinationEvent(trait);
                break;
        }

    }

    /**
     * updates number of enforce actions on current user
     */
    public void updateEnforcedActions() {
        Alias alias = DataModel.getInstance().getCurrentAlias();
        int newEnforcerTotal = alias.getEnforcerActions() + 1;
        alias.setEnforcerActions(newEnforcerTotal);
        DataModel.getInstance().saveAliasData(alias);
    }
}
