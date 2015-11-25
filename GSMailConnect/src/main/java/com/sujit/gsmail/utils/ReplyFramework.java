package com.sujit.gsmail.utils;

public class ReplyFramework {

	public static void main(String[] args) {
		
	}
	
	public static String getReplyFormattedMailStringForAutosys(String replyHTMLBody, String fromName, String sentStamp, String toName, String ccName, String subject, String orgBodyHTML){
		String headerPart = "<html xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" xmlns:m=\"http://schemas.microsoft.com/office/2004/12/omml\" xmlns=\"http://www.w3.org/TR/REC-html40\"><head><META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=us-ascii\"><meta name=Generator content=\"Microsoft Word 14 (filtered medium)\"><style><!-- /* Font Definitions */ @font-face 	{font-family:Calibri; 	panose-1:2 15 5 2 2 2 4 3 2 4;} @font-face 	{font-family:Tahoma; 	panose-1:2 11 6 4 3 5 4 4 2 4;} /* Style Definitions */ p.MsoNormal, li.MsoNormal, div.MsoNormal 	{margin:0in; 	margin-bottom:.0001pt; 	font-size:11.0pt; 	font-family:\"Calibri\",\"sans-serif\";} a:link, span.MsoHyperlink 	{mso-style-priority:99; 	color:blue; 	text-decoration:underline;} a:visited, span.MsoHyperlinkFollowed 	{mso-style-priority:99; 	color:purple; 	text-decoration:underline;} span.EmailStyle17 	{mso-style-type:personal; 	font-family:\"Calibri\",\"sans-serif\"; 	color:windowtext;} span.EmailStyle18 	{mso-style-type:personal-reply; 	font-family:\"Calibri\",\"sans-serif\"; 	color:#1F497D;} .MsoChpDefault 	{mso-style-type:export-only; 	font-size:10.0pt;} @page WordSection1 	{size:8.5in 11.0in; 	margin:1.0in 1.0in 1.0in 1.0in;} div.WordSection1 	{page:WordSection1;} --></style><!--[if gte mso 9]><xml> <o:shapedefaults v:ext=\"edit\" spidmax=\"1026\" /> </xml><![endif]--><!--[if gte mso 9]><xml> <o:shapelayout v:ext=\"edit\"> <o:idmap v:ext=\"edit\" data=\"1\" /> </o:shapelayout></xml><![endif]--></head><body lang=EN-US link=blue vlink=purple><div class=WordSection1>";
    	
    	StringBuilder replySb = new StringBuilder();
    	replySb.append("<p class=MsoNormal><span style='color:#1F497D'>");
    	replySb.append("Hi,").append("<br><br>");
    	replySb.append("We will check the job failure(s), if any. Current status of the job(s) are - <br><br>")
    	.append(replyHTMLBody)
    	.append("<br><br>Thanks,<br>OE Team<o:p></o:p></span></p>");
    	replySb.append("<p class=MsoNormal><span style='color:#1F497D'><o:p>&nbsp;</o:p></span></p>");
    	
    	StringBuilder trackHeaderSb = new StringBuilder();
    	trackHeaderSb.append("<div><div style='border:none;border-top:solid #B5C4DF 1.0pt;padding:3.0pt 0in 0in 0in'><p class=MsoNormal><b><span style='font-size:10.0pt;font-family:\"Tahoma\",\"sans-serif\"'>From:</span></b><span style='font-size:10.0pt;font-family:\"Tahoma\",\"sans-serif\"'>");
    	trackHeaderSb.append(" "+fromName).append("<br><b>Sent:</b> ");
    	trackHeaderSb.append(" "+sentStamp).append("<br><b>To:</b>");
    	trackHeaderSb.append(" "+toName).append("<br><b>Cc:</b>");
    	trackHeaderSb.append(" "+ccName).append("<br><b>Subject:</b>");
    	trackHeaderSb.append(" "+subject).append("<o:p></o:p></span></p></div></div>");
    	
    	StringBuilder finalReplyMailSb = new StringBuilder();
    	finalReplyMailSb.append(headerPart).append(replySb.toString()).append(trackHeaderSb.toString()).append("<p class=MsoNormal><o:p>&nbsp;</o:p></p>").append(orgBodyHTML).append("</div></body></html>");
    	
    	return finalReplyMailSb.toString();
	}
	
	public static String getReplyFormattedMailStringForIndividualPush(String replyHTMLBody, String fromName, String sentStamp, String toName, String ccName, String subject, String orgBodyHTML){
String headerPart = "<html xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" xmlns:m=\"http://schemas.microsoft.com/office/2004/12/omml\" xmlns=\"http://www.w3.org/TR/REC-html40\"><head><META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=us-ascii\"><meta name=Generator content=\"Microsoft Word 14 (filtered medium)\"><style><!-- /* Font Definitions */ @font-face 	{font-family:Calibri; 	panose-1:2 15 5 2 2 2 4 3 2 4;} @font-face 	{font-family:Tahoma; 	panose-1:2 11 6 4 3 5 4 4 2 4;} /* Style Definitions */ p.MsoNormal, li.MsoNormal, div.MsoNormal 	{margin:0in; 	margin-bottom:.0001pt; 	font-size:11.0pt; 	font-family:\"Calibri\",\"sans-serif\";} a:link, span.MsoHyperlink 	{mso-style-priority:99; 	color:blue; 	text-decoration:underline;} a:visited, span.MsoHyperlinkFollowed 	{mso-style-priority:99; 	color:purple; 	text-decoration:underline;} span.EmailStyle17 	{mso-style-type:personal; 	font-family:\"Calibri\",\"sans-serif\"; 	color:windowtext;} span.EmailStyle18 	{mso-style-type:personal-reply; 	font-family:\"Calibri\",\"sans-serif\"; 	color:#1F497D;} .MsoChpDefault 	{mso-style-type:export-only; 	font-size:10.0pt;} @page WordSection1 	{size:8.5in 11.0in; 	margin:1.0in 1.0in 1.0in 1.0in;} div.WordSection1 	{page:WordSection1;} --></style><!--[if gte mso 9]><xml> <o:shapedefaults v:ext=\"edit\" spidmax=\"1026\" /> </xml><![endif]--><!--[if gte mso 9]><xml> <o:shapelayout v:ext=\"edit\"> <o:idmap v:ext=\"edit\" data=\"1\" /> </o:shapelayout></xml><![endif]--></head><body lang=EN-US link=blue vlink=purple><div class=WordSection1>";
    	
    	StringBuilder replySb = new StringBuilder();
    	replySb.append("<p class=MsoNormal><span style='color:#1F497D'>");
    	replySb.append("Hi,").append("<br><br>");
    	replySb.append(replyHTMLBody)
    	.append("<br><br>Thanks,<br>Build Team<o:p></o:p></span></p>");
    	replySb.append("<p class=MsoNormal><span style='color:#1F497D'><o:p>&nbsp;</o:p></span></p>");
    	
    	StringBuilder trackHeaderSb = new StringBuilder();
    	trackHeaderSb.append("<div><div style='border:none;border-top:solid #B5C4DF 1.0pt;padding:3.0pt 0in 0in 0in'><p class=MsoNormal><b><span style='font-size:10.0pt;font-family:\"Tahoma\",\"sans-serif\"'>From:</span></b><span style='font-size:10.0pt;font-family:\"Tahoma\",\"sans-serif\"'>");
    	trackHeaderSb.append(" "+fromName).append("<br><b>Sent:</b> ");
    	trackHeaderSb.append(" "+sentStamp).append("<br><b>To:</b>");
    	trackHeaderSb.append(" "+toName).append("<br><b>Cc:</b>");
    	trackHeaderSb.append(" "+((ccName==null)?"":ccName)).append("<br><b>Subject:</b>");
    	trackHeaderSb.append(" "+subject).append("<o:p></o:p></span></p></div></div>");
    	
    	StringBuilder finalReplyMailSb = new StringBuilder();
    	finalReplyMailSb.append(headerPart).append(replySb.toString()).append(trackHeaderSb.toString()).append("<p class=MsoNormal><o:p>&nbsp;</o:p></p>").append(orgBodyHTML).append("</div></body></html>");
    	
    	return finalReplyMailSb.toString();
	}
	
	public static String getReplyFormattedMailStringForPeriodicBuild(String replyHTMLBody, String fromName, String sentStamp, String toName, String ccName, String subject, String orgBodyHTML){
		String headerPart = "<html xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" xmlns:m=\"http://schemas.microsoft.com/office/2004/12/omml\" xmlns=\"http://www.w3.org/TR/REC-html40\"><head><META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=us-ascii\"><meta name=Generator content=\"Microsoft Word 14 (filtered medium)\"><style><!-- /* Font Definitions */ @font-face 	{font-family:Calibri; 	panose-1:2 15 5 2 2 2 4 3 2 4;} @font-face 	{font-family:Tahoma; 	panose-1:2 11 6 4 3 5 4 4 2 4;} /* Style Definitions */ p.MsoNormal, li.MsoNormal, div.MsoNormal 	{margin:0in; 	margin-bottom:.0001pt; 	font-size:11.0pt; 	font-family:\"Calibri\",\"sans-serif\";} a:link, span.MsoHyperlink 	{mso-style-priority:99; 	color:blue; 	text-decoration:underline;} a:visited, span.MsoHyperlinkFollowed 	{mso-style-priority:99; 	color:purple; 	text-decoration:underline;} span.EmailStyle17 	{mso-style-type:personal; 	font-family:\"Calibri\",\"sans-serif\"; 	color:windowtext;} span.EmailStyle18 	{mso-style-type:personal-reply; 	font-family:\"Calibri\",\"sans-serif\"; 	color:#1F497D;} .MsoChpDefault 	{mso-style-type:export-only; 	font-size:10.0pt;} @page WordSection1 	{size:8.5in 11.0in; 	margin:1.0in 1.0in 1.0in 1.0in;} div.WordSection1 	{page:WordSection1;} --></style><!--[if gte mso 9]><xml> <o:shapedefaults v:ext=\"edit\" spidmax=\"1026\" /> </xml><![endif]--><!--[if gte mso 9]><xml> <o:shapelayout v:ext=\"edit\"> <o:idmap v:ext=\"edit\" data=\"1\" /> </o:shapelayout></xml><![endif]--></head><body lang=EN-US link=blue vlink=purple><div class=WordSection1>";
		    	
		    	StringBuilder replySb = new StringBuilder();
		    	replySb.append("<p class=MsoNormal><span style='color:#1F497D'>");
		    	replySb.append("Hi,").append("<br><br>");
		    	replySb.append(replyHTMLBody).append("<br><br>Thanks,<br>OE Team<o:p></o:p></span></p>");
		    	replySb.append("<p class=MsoNormal><span style='color:#1F497D'><o:p>&nbsp;</o:p></span></p>");
		    	
		    	StringBuilder trackHeaderSb = new StringBuilder();
		    	trackHeaderSb.append("<div><div style='border:none;border-top:solid #B5C4DF 1.0pt;padding:3.0pt 0in 0in 0in'><p class=MsoNormal><b><span style='font-size:10.0pt;font-family:\"Tahoma\",\"sans-serif\"'>From:</span></b><span style='font-size:10.0pt;font-family:\"Tahoma\",\"sans-serif\"'>");
		    	trackHeaderSb.append(" "+fromName).append("<br><b>Sent:</b> ");
		    	trackHeaderSb.append(" "+sentStamp).append("<br><b>To:</b>");
		    	trackHeaderSb.append(" "+toName).append("<br><b>Cc:</b>");
		    	trackHeaderSb.append(" "+ccName).append("<br><b>Subject:</b>");
		    	trackHeaderSb.append(" "+subject).append("<o:p></o:p></span></p></div></div>");
		    	
		    	StringBuilder finalReplyMailSb = new StringBuilder();
		    	finalReplyMailSb.append(headerPart).append(replySb.toString()).append(trackHeaderSb.toString()).append("<p class=MsoNormal><o:p>&nbsp;</o:p></p>").append(orgBodyHTML).append("</div></body></html>");
		    	
		    	return finalReplyMailSb.toString();
			}

}
