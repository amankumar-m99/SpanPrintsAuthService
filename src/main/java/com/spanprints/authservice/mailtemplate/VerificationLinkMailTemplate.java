package com.spanprints.authservice.mailtemplate;

public class VerificationLinkMailTemplate {

	public static String buildHtmlContent(String appName, String supportEmail, String activationLink, String expiresAt,
			String username) {
		return """
								<!doctype html><html lang="en"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width">
				<title>Activate your account</title><style>
				body {margin: 0;padding: 0;background-color: #f4f6f8;-webkit-font-smoothing: antialiased;
				  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial;}
				.header {padding: 20px;color: #fff;text-align: center;background: linear-gradient(90deg, #4f46e5, #06b6d4);}
				.header h1 {margin: 0;font-size: 20px;font-weight: 600;letter-spacing: -0.2px;}
				.content {padding: 28px 32px;color: #111827;line-height: 1.45;font-size: 15px;}
				.btn {display: inline-block;margin: 18px 0;padding: 12px 20px;border-radius: 8px;text-decoration: none;
				  font-weight: 600;background-color: #2563eb;color: #ffffff;}
				.note {font-size: 13px;color: #6b7280;}
				.token-box {display: block;background: #f8fafc;border: 1px dashed #e6eef9;padding: 12px 14px;
				  margin: 12px 0;border-radius: 6px;font-family: monospace;word-break: break-all;}
				.footer {padding: 18px 32px;font-size: 13px;color: #6b7280;border-top: 1px solid #eef2f7;text-align: center;}
				a.support {color: #2563eb;text-decoration: none;}@media (max-width: 420px) {.content {padding: 20px;}.footer {padding: 16px 20px;}}
				</style></head><body><div role="article" aria-label="Account activation"
				style="width: 100%%;max-width: 580px;margin: 24px auto;background: white;border-radius: 8px;overflow: hidden;box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);">
				<div class="header"><h1>Activate your %s account</h1></div><div class="content"><p style="margin-top: 0;font-size: 16px;font-weight: 600;">Hi %s,</p>
				<p>Thanks for creating an account with <strong>%s</strong>. To complete your registration and activate your account,
				  please confirm your email address by clicking the button below (expires at %s):</p>
				<p style="text-align: center;" class="btn-div"><a href="%s" class="btn" target="_blank" rel="noopener">Activate my account</a></p>
				<p class="note">If the button doesn't work, copy and paste the following link into your browser:<br>
				<a href="%s" target="_blank" rel="noopener" class="support">%s</a></p><hr style="border:none;border-top:1px solid #f1f5f9;margin:18px 0;">
				<p class="note">If you didn't create an account with <strong>%s</strong>, you can safely ignore this email. No action is required.</p>
				<p style="margin-top:18px;">Need help? Contact our support at <a href="mailto:%s" class="support">%s</a>.</p></div>
				<div class="footer"><div>&copy; %d %s. All rights reserved.</div><div style="margin-top:8px;">
				If you did not request this email, you can safely ignore it.</div></div></div></body></html>
										"""
				.formatted(appName, username, appName, expiresAt, activationLink, activationLink, activationLink,
						appName, supportEmail, supportEmail, java.time.Year.now().getValue(), appName);
	}

	public static String buildPlainTextContent(String firstName, String activationLink, String expiresAt,
			String supportEmail, String appName) {
		return """
				Hi %s,

				Thanks for creating an account with %s. To activate your account, open this link (expires at %s ):

				%s

				If you didn’t sign up, ignore this email.
				Need help? Contact: %s
				""".formatted(firstName, appName, expiresAt, activationLink, supportEmail);
	}
}
