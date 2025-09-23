package com.example.app.notifications;

import com.example.app.reporting.TestResultAnalyzer;
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.Attachment;
import com.slack.api.model.Field;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * SlackNotifier - Send test results and notifications to Slack channels
 * Integrates with CI/CD pipelines to provide real-time test status updates
 */
public class SlackNotifier {
    private final Slack slack;
    private final String botToken;
    private final String defaultChannel;
    
    public SlackNotifier(String botToken, String defaultChannel) {
        this.slack = Slack.getInstance();
        this.botToken = botToken;
        this.defaultChannel = defaultChannel;
    }
    
    /**
     * Send test completion notification with summary
     */
    public void sendTestCompletionNotification(TestResultAnalyzer.TestSummary summary) {
        try {
            String message = buildTestCompletionMessage(summary);
            List<Attachment> attachments = buildTestSummaryAttachments(summary);
            
            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel(defaultChannel)
                .text(message)
                .attachments(attachments)
                .build();
                
            ChatPostMessageResponse response = slack.methods(botToken).chatPostMessage(request);
            
            if (!response.isOk()) {
                System.err.println("Failed to send Slack notification: " + response.getError());
            }
            
        } catch (IOException | SlackApiException e) {
            System.err.println("Error sending Slack notification: " + e.getMessage());
        }
    }
    
    /**
     * Send test failure alert with details
     */
    public void sendTestFailureAlert(String testName, String failureReason, String environment) {
        try {
            String message = "🚨 *Test Failure Alert* 🚨";
            
            List<Attachment> attachments = new ArrayList<>();
            Attachment attachment = Attachment.builder()
                .color("danger")
                .title("Test: " + testName)
                .text("Environment: " + environment)
                .addFields(
                    Field.builder().title("Failure Reason").value(failureReason).valueShortEnough(false).build(),
                    Field.builder().title("Environment").value(environment).valueShortEnough(true).build(),
                    Field.builder().title("Action Required").value("Please investigate and fix").valueShortEnough(true).build()
                )
                .build();
            attachments.add(attachment);
            
            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel(defaultChannel)
                .text(message)
                .attachments(attachments)
                .build();
                
            slack.methods(botToken).chatPostMessage(request);
            
        } catch (IOException | SlackApiException e) {
            System.err.println("Error sending failure alert: " + e.getMessage());
        }
    }
    
    /**
     * Send CI/CD pipeline status notification
     */
    public void sendPipelineStatusNotification(String pipelineName, String status, String buildUrl) {
        try {
            String emoji = status.equals("success") ? "✅" : "❌";
            String color = status.equals("success") ? "good" : "danger";
            String message = emoji + " *CI/CD Pipeline " + status.toUpperCase() + "*";
            
            List<Attachment> attachments = new ArrayList<>();
            Attachment.AttachmentBuilder attachmentBuilder = Attachment.builder()
                .color(color)
                .title("Pipeline: " + pipelineName)
                .addFields(
                    Field.builder().title("Status").value(status).valueShortEnough(true).build(),
                    Field.builder().title("Environment").value(System.getProperty("env", "dev")).valueShortEnough(true).build()
                );
                
            if (buildUrl != null && !buildUrl.isEmpty()) {
                attachmentBuilder.titleLink(buildUrl);
                attachmentBuilder.addFields(
                    Field.builder().title("Build URL").value("<" + buildUrl + "|View Build>").valueShortEnough(false).build()
                );
            }
            
            attachments.add(attachmentBuilder.build());
            
            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel(defaultChannel)
                .text(message)
                .attachments(attachments)
                .build();
                
            slack.methods(botToken).chatPostMessage(request);
            
        } catch (IOException | SlackApiException e) {
            System.err.println("Error sending pipeline notification: " + e.getMessage());
        }
    }
    
    private String buildTestCompletionMessage(TestResultAnalyzer.TestSummary summary) {
        if (summary.getFailedTests() == 0) {
            return "✅ *All Tests Passed!* 🎉";
        } else {
            return "⚠️ *Test Execution Completed with Failures*";
        }
    }
    
    private List<Attachment> buildTestSummaryAttachments(TestResultAnalyzer.TestSummary summary) {
        List<Attachment> attachments = new ArrayList<>();
        
        String color = summary.getFailedTests() == 0 ? "good" : "warning";
        
        Attachment summaryAttachment = Attachment.builder()
            .color(color)
            .title("Test Execution Summary")
            .addFields(
                Field.builder().title("Total Tests").value(String.valueOf(summary.getTotalTests())).valueShortEnough(true).build(),
                Field.builder().title("Passed").value(String.valueOf(summary.getPassedTests())).valueShortEnough(true).build(),
                Field.builder().title("Failed").value(String.valueOf(summary.getFailedTests())).valueShortEnough(true).build(),
                Field.builder().title("Skipped").value(String.valueOf(summary.getSkippedTests())).valueShortEnough(true).build(),
                Field.builder().title("Success Rate").value(summary.getSuccessRate() + "%").valueShortEnough(true).build(),
                Field.builder().title("Duration").value(summary.getExecutionTime() + "ms").valueShortEnough(true).build(),
                Field.builder().title("Environment").value(System.getProperty("env", "dev")).valueShortEnough(true).build()
            )
            .build();
            
        attachments.add(summaryAttachment);
        
        // Add failed test details if any
        if (summary.getFailedTests() > 0 && summary.getFailedTestNames() != null) {
            Attachment failureAttachment = Attachment.builder()
                .color("danger")
                .title("Failed Tests")
                .text("```" + String.join("\n", summary.getFailedTestNames()) + "```")
                .build();
            attachments.add(failureAttachment);
        }
        
        return attachments;
    }
}