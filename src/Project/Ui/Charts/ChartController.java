package Project.Ui.Charts;


import Project.Logic.DataBase.SQL.IssuesTransitionSql;

import Project.Logic.Status;


public class ChartController {
    public static int findAmountOFRejectedIssues(int projectId, int userId){
        return IssuesTransitionSql.getInstance().getTransitionIdsWithStatusTransition(projectId,userId
                , Status.QA.toString(),Status.TODO.toString()).size()+
                IssuesTransitionSql.getInstance().getTransitionIdsWithStatusTransition(projectId,userId
                        , Status.QA.toString(),Status.IN_PROGRESS.toString()).size();
    }
    public static int findAmountOFCompletedIssues(int projectId, int userId){
        return IssuesTransitionSql.getInstance().getTransitionIdsWithStatusTransition(projectId,userId
                , Status.QA.toString(),Status.DONE.toString()).size();
    }
}
