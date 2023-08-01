package Project.Logic;

public enum Role {
    SUPER_ADMIN(new FeatureAccess[]{FeatureAccess.CREATE_PROJECT, FeatureAccess.PROJECT_SETTING,FeatureAccess.MOVE_EVERYWHERE}),
    PROJECT_OWNER(new FeatureAccess[]{FeatureAccess.CREATE_PROJECT, FeatureAccess.PROJECT_SETTING,FeatureAccess.MOVE_EVERYWHERE}),
    DEVELOPER(new FeatureAccess[]{FeatureAccess.MOVE_TO_QA}),
    QA(new FeatureAccess[]{FeatureAccess.MOVE_FROM_QA});
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
