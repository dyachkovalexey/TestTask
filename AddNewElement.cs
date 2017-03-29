using System;
using System.Data.SqlClient;
using System.Net.Mail;
using System.Net;
using System.Windows.Forms;

namespace WindowsFormsApplication1
{
    class AddNewElement
    {
        SqlConnection sqlConnection = new SqlConnection("Connection String");
        SqlCommand cmd = new SqlCommand();


        /// <summary>
        /// Add New Post
        /// </summary>
        public void AddToDBNewPost(String Name)
        {
            String checkName = "SELECT post_id FROM Post WHERE post_name =\'" + Name + "\';";
            SqlCommand command = new SqlCommand(checkName, sqlConnection);
            sqlConnection.Open();
            Int32 result = command.ExecuteNonQuery();
            if (result != 0) 
            {
                    SendMailToModerator("MyBlog@gmail.com", "New Post", "User " + Name + " added new Post, please, check it.");
                    //TODO: Add post to DB
            }
            else
            {
                MessageBox.Show("Sorry, but this post allready created");
            }
        }

        /// <summary>
        /// Send mail to Moderator
        /// </summary>
        public static void SendMailToModerator(string from, string caption, string message)
        {
            try
            {
                MailMessage mail = new MailMessage();
                mail.From = new MailAddress(from);
                mail.To.Add(new MailAddress("BlogModerator@gmail.com"));
                mail.Subject = caption;
                mail.Body = message;
                SmtpClient client = new SmtpClient();
                client.Host = "smtp.gmail.com";
                client.Port = 587;
                client.EnableSsl = true;
                client.Credentials = new NetworkCredential(from.Split('@')[0], "qwerty12345");
                client.DeliveryMethod = SmtpDeliveryMethod.Network;
                client.Send(mail);
                mail.Dispose();
            }
            catch(Exception e)
            {
                throw new Exception("Mail.Send: " + e.Message);
            }

        }

    }
}
