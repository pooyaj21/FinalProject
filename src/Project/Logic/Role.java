package Project.Logic;

public enum Role {
    SUPER_ADMIN(new FeatureAccess[]{FeatureAccess.CREATE_PROJECT, FeatureAccess.PROJECT_SETTING
            ,FeatureAccess.MOVE_EVERYWHERE,FeatureAccess.ADD_ISSUES,FeatureAccess.EDIT_ISSUES,FeatureAccess.ADD_BOARD
            ,FeatureAccess.EDIT_BOARD,FeatureAccess.DELETE_BOARD,FeatureAccess.DELETE_ISSUES}),

    PROJECT_OWNER(new FeatureAccess[]{FeatureAccess.MOVE_EVERYWHERE,FeatureAccess.ADD_ISSUES,FeatureAccess.EDIT_ISSUES
            ,FeatureAccess.ADD_BOARD,FeatureAccess.EDIT_BOARD}),

    DEVELOPER(new FeatureAccess[]{FeatureAccess.MOVE_TO_QA}),

    QA(new FeatureAccess[]{FeatureAccess.MOVE_FROM_QA,FeatureAccess.ADD_BUG,FeatureAccess.EDIT_BUG});
    private final FeatureAccess[] featureAccesses;

    Role(FeatureAccess[] featureAccesses) {
        this.featureAccesses = featureAccesses;
    }

    public boolean hasAccess(FeatureAccess featureAccess) {
        for (FeatureAccess f : featureAccesses) {
            if (f == featureAccess) return true;
        }
        return false;
    }
}
