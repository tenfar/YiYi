package com.tenfar.yiyi.util;


/**
 * 全局id生成器
 *
 * @author sumory.wu
 */
public class IDUtils {

    private static IdWorker fileIdWorker = new IdWorker(1);
    private static IdWorker userIdWorker = new IdWorker(2);
    private static IdWorker folderIdWorker = new IdWorker(3);
    private static IdWorker physcialIdWorker = new IdWorker(4);
    private static IdWorker appIdWorker = new IdWorker(5);
    private static IdWorker storeIdWorker = new IdWorker(6);
    private static IdWorker broadcastIdWorker = new IdWorker(7);
    private static IdWorker contactTagIdWorker = new IdWorker(8);
    private static IdWorker fileTagIdWorker = new IdWorker(9);
    private static IdWorker msgIdWorker = new IdWorker(10);
    private static IdWorker calendarIdWorker = new IdWorker(11);
    private static IdWorker assignmentIdWorker = new IdWorker(12);
    private static IdWorker answerIdWorker = new IdWorker(13);
    private static IdWorker noticeIdWorker = new IdWorker(14);
    private static IdWorker judgementIdWorker = new IdWorker(15);
    private static IdWorker orderIdWorker = new IdWorker(16);

    public static String generateFileId() throws Exception {
        return String.valueOf(fileIdWorker.nextId());
    }

    public static String generateUserId() throws Exception {

        return String.valueOf(userIdWorker.nextId());
    }

    public static String generateFolderId() throws Exception {
        return String.valueOf(folderIdWorker.nextId());
    }

    public static String generatePhyscialFileId() throws Exception {
        return String.valueOf(physcialIdWorker.nextId());
    }

    public static String getnerateVirtualFolderId() throws Exception {
        return String.valueOf(appIdWorker.nextId());
    }

    public static String generateVirtualFileId() throws Exception {
        return String.valueOf(storeIdWorker.nextId());
    }

    public static String generateShareId() throws Exception {
        return String.valueOf(broadcastIdWorker.nextId());
    }

    public static String generateDeleteId() throws Exception {
        return String.valueOf(contactTagIdWorker.nextId());
    }

    public static String generateFileTagId() throws Exception {
        return String.valueOf(fileTagIdWorker.nextId());
    }

    public static String generateMsgId() throws Exception {
        return String.valueOf(msgIdWorker.nextId());
    }

    public static String generateCalendarId() throws Exception {
        return String.valueOf(calendarIdWorker.nextId());
    }

    public static String generateAssignmentId() throws Exception {
        return String.valueOf(assignmentIdWorker.nextId());
    }

    public static String generateAnswerId() throws Exception {
        return String.valueOf(answerIdWorker.nextId());
    }

    public static String generateNoticeId() throws Exception {
        return String.valueOf(noticeIdWorker.nextId());
    }

    public static String generateJudgementId() throws Exception {
        return String.valueOf(judgementIdWorker.nextId());
    }

    public static String generateOrderId() throws Exception {
        return String.valueOf(orderIdWorker.nextId());
    }
}
