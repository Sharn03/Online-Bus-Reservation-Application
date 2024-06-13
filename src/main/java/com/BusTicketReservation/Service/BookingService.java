package com.BusTicketReservation.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.BusTicketReservation.Entity.Booking;

import com.BusTicketReservation.Repository.BookingRepository;
import com.lowagie.text.DocumentException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
@Service
public class BookingService {
	@Autowired
	private BookingRepository bookingRepo;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private TemplateEngine templateEngine;
	
		
	//method to save the confirmed bookings;
	public void saveBooking(Booking booking)
	{
		bookingRepo.save(booking);
		
	}
	
	
	//Method to get all the bookings
	public List<Booking> getAllBookings()
	{
		List<Booking>allBookings = bookingRepo.findAll();
		return allBookings;
	}
	
	
	//Method to deleteBookingHistory
	public void deleteBookingHistory(int id)
	{
		if(Objects.nonNull(id))
				{
					bookingRepo.deleteById(id);
				}
		
	}
	
	//Method to get booking by id
	public Booking getBookingById(int id)
	{
		Booking booking = null;
		if(Objects.nonNull(id))
		{
			Optional<Booking>optionalBooking = bookingRepo.findById(id);
			if(optionalBooking.isPresent())
			{
				booking = optionalBooking.get();
			}
			else
			{
				throw new RuntimeException("Booking ID "+ id +" Not Found ");
			}
		}
		return booking;
	}
	
	
	
	
	//Method to get Generate e ticket and send via mail
	public void generateAndSendTicket(Booking booking)
	{
		try
		{
			byte[]ticketPdf = generateTicketPDF(booking);
			
			
			sendTicketViaEmail(booking.getEmailId(),ticketPdf);
		}
		
		catch(IOException | MessagingException e)
		{
			e.printStackTrace();
		}
	}
	
	 // Generate ticket PDF
    public byte[] generateTicketPDF(Booking booking) throws IOException
    {
        String htmlContent = renderHtmlTemplate(booking);
        return htmlToPdf(htmlContent);
    }
    
    
    // Render Thymeleaf template to HTML
    private String renderHtmlTemplate(Booking booking)
    	{
        Context context = new Context();
        context.setVariable("booking", booking);
        return templateEngine.process("ticketTemplate", context);
    }
    
 
    // Convert HTML to PDF
    private byte[] htmlToPdf(String htmlContent) throws IOException
    	{
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            try {
				renderer.createPDF(outputStream);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return outputStream.toByteArray();
        }
    }
    
    
    
    // Send ticket via email
    private void sendTicketViaEmail(String recipientEmail, byte[] ticketPdf) throws MessagingException 
    
    	{
    	
    	if(recipientEmail != null && !recipientEmail.isEmpty())
    	{
    		MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(recipientEmail);
            helper.setSubject("Your e-ticket");
            helper.setText("Please find your e-ticket attached.");

            helper.addAttachment("e-ticket.pdf", new ByteArrayResource(ticketPdf), "application/pdf");

            javaMailSender.send(message);
    	}
    	
    	else
    	{
    		throw new IllegalArgumentException("Recipient email address is empty or null.");
    	}
        
    }


	public List<Booking> getBookingsByUserEmail(String userEmail) {
		return bookingRepo.findBookingByEmailId(userEmail);
	}
    

}
