package fi.metropolia.group8.model;

/**
 * Handles all enforcement actions
 */
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

    public EnforceManager() {}


    /**
     * Attempts to threaten the debtor as one of the enforcement action options
     * @param loan loan object which contains reference to victim
     */
    public void Threaten(Loan loan) {
        Victim victim = loan.getVictim();
        VictimTraits trait = VictimTraits.valueOf(victim.getTrait());

        switch (trait) {
            case NORMIE:
                float newInterest = loan.getInterest() + 15;
                loan.setInterest(newInterest);
                DataModel.getInstance().saveLoanData(loan);
                EventManager.getInstance().threatEvent(victim);
                break;
            case SCARED:
                loan.setDueDate(DataModel.getInstance().getCurrentUser().getCurrentDate().plusDays(1));
                DataModel.getInstance().saveLoanData(loan);
                EventManager.getInstance().threatEvent(victim);
                break;
            case JUNKIE:
            case VIOLENT:
                EventManager.getInstance().threatEvent(victim);
                break;
            default:
                EventManager.getInstance().threatEvent(victim);
                loan.setDueDate(loan.getDueDate().minusDays(1));
                DataModel.getInstance().saveLoanData(loan);
                break;
        }
    }

    /**
     * Attempts to blackmail the debtor as one of the enforcement action options
     * @param loan loan object which contains reference to victim
     */
    public void Extort(Loan loan) {
        Victim victim = loan.getVictim();
        VictimTraits trait = VictimTraits.valueOf(victim.getTrait());

        switch (trait){
            case SCARED:
                LoanCalculator.getInstance().completeLoan(loan);
                EventManager.getInstance().extortionEvent(victim);
                break;
            default:
                // Default behavior is to pay back half now
                LoanCalculator.getInstance().payHalfLoan(loan);
                EventManager.getInstance().extortionEvent(victim);
                break;
        }

    }

    /**
     * Attempts to torture the debtor as one of the enforcement action options
     * @param loan loan object which contains reference to victim
     */
    public void Torture(Loan loan) {
        Victim victim = loan.getVictim();
        VictimTraits trait = VictimTraits.valueOf(victim.getTrait());
        float modifier = 20f;

        switch (trait){
            case SCARED:
                // SCARED victims die to a heart attack.
                LoanCalculator.getInstance().repoLoan(DataModel.getInstance().getCurrentAlias(), loan);
                victim.setAlive(false);
                EventManager.getInstance().tortureEvent(victim, modifier);
                break;
            default:
                // Default behavior: Modify the loan's original value through a percentage modifier.
                LoanCalculator.getInstance().modifyLoanValue(loan, modifier);
                EventManager.getInstance().tortureEvent(victim, modifier);
                break;
        }

    }

    /**
     * Attempts to assassinate the debtor as one of the enforcement action options
     * @param loan loan object which contains reference to victim
     */
    public void Assassinate(Loan loan) {
        Victim victim = loan.getVictim();
        VictimTraits trait = VictimTraits.valueOf(victim.getTrait());

        switch (trait) {
            case SNEAKY:
                LoanCalculator.getInstance().forfeitLoan(DataModel.getInstance().getCurrentAlias(), loan);
                EventManager.getInstance().assassinationEvent(victim);
                break;
            case VIOLENT:
                LoanCalculator.getInstance().forfeitLoan(DataModel.getInstance().getCurrentAlias(), loan);
                victim.setAlive(false);
                DataModel.getInstance().saveVictimData(victim);
                EventManager.getInstance().assassinationEvent(victim);
                break;
            default:
                LoanCalculator.getInstance().repoLoan(DataModel.getInstance().getCurrentAlias(), loan);
                victim.setAlive(false);
                DataModel.getInstance().saveVictimData(victim);
                EventManager.getInstance().assassinationEvent(victim);
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
