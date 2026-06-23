package com.library.library_system.config;

import com.library.library_system.entity.Book;
import com.library.library_system.entity.User;
import com.library.library_system.repository.BookRepository;
import com.library.library_system.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner loadData(UserRepository userRepository, BookRepository bookRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                User student = new User();
                student.setName("Student Name");
                student.setEmail("student@library.com");
                student.setPassword("password123");
                student.setRole(User.Role.STUDENT);
                userRepository.save(student);

                User teacher = new User();
                teacher.setName("Teacher Name");
                teacher.setEmail("teacher@library.com");
                teacher.setPassword("password123");
                teacher.setRole(User.Role.TEACHER);
                userRepository.save(teacher);

                User admin = new User();
                admin.setName("Admin Librarian");
                admin.setEmail("admin@library.com");
                admin.setPassword("admin123");
                admin.setRole(User.Role.ADMIN);
                userRepository.save(admin);
            }

            if (bookRepository.count() == 0) {
                Object[][] bookData = {
                    {"The Silent Hour", "Emily Carter", "Fiction", "https://is1-ssl.mzstatic.com/image/thumb/Publication221/v4/fa/10/55/fa105559-47e6-d135-3f8c-77366d022aed/0003163918.jpg/600x600bb.jpg"},
                    {"Beneath the Crimson Sky", "Daniel Moore", "Fiction", "https://is1-ssl.mzstatic.com/image/thumb/Publication221/v4/74/6e/b4/746eb4f2-0116-5f62-50f7-34c1677205d1/0003026331.jpg/600x600bb.jpg"},
                    {"Whispers in the Fog", "Sophia Lane", "Fiction", "https://is1-ssl.mzstatic.com/image/thumb/Publication221/v4/f8/ba/4f/f8ba4f3b-44e1-20e2-7a7e-3c20f66f3b38/0002777176.jpg/600x600bb.jpg"},
                    {"The Last Letter Home", "Olivia Reed", "Fiction", "https://is1-ssl.mzstatic.com/image/thumb/Publication116/v4/a1/4f/a1/a14fa1ba-5534-0a77-8667-b3015898a0b0/9781640635340.jpg/600x600bb.jpg"},
                    {"Echoes of Yesterday", "Mason Brooks", "Fiction", "https://is1-ssl.mzstatic.com/image/thumb/Publication221/v4/67/7a/38/677a3810-d987-3e28-7486-910706f64898/9798218327323.jpg/600x600bb.jpg"},
                    {"A House of Secrets", "Isabella Hayes", "Fiction", "https://is1-ssl.mzstatic.com/image/thumb/Publication/v4/1d/95/09/1d950920-20f6-c9b9-68d5-b28a4291ae15/9780062192486.jpg/600x600bb.jpg"},
                    {"The Forgotten Path", "Liam Turner", "Fiction", "https://is1-ssl.mzstatic.com/image/thumb/Publication124/v4/e4/0d/10/e40d10dc-e040-02d6-2020-64e87d9818fe/ForgottenPath_Bolden_iBooks.jpg/600x600bb.jpg"},
                    {"Shadows of the Mind", "Ava Collins", "Fiction", "https://is1-ssl.mzstatic.com/image/thumb/Publication/v4/1f/5f/e6/1f5fe668-dc5a-7f2c-c633-d7054e51b9f8/12871130.jpg/600x600bb.jpg"},
                    {"Before the Rain Falls", "Noah Bennett", "Fiction", "https://is1-ssl.mzstatic.com/image/thumb/Publication4/v4/35/c5/47/35c54781-83fe-0e0f-5cf9-c93296180a80/9780307268556.jpg/600x600bb.jpg"},
                    {"The Midnight Promise", "Charlotte Evans", "Fiction", "https://is1-ssl.mzstatic.com/image/thumb/Publication211/v4/54/6d/ee/546deea2-fdfa-3fd2-8181-c1c21d8568f0/1058765338.jpg/600x600bb.jpg"},
                    {"Murder at Raven Manor", "Arthur Wells", "Mystery", "https://is1-ssl.mzstatic.com/image/thumb/Publication211/v4/70/31/b3/7031b30a-d4a1-3625-acbf-076142b9d6d2/673b2a01-f095-485c-8ac1-a93a59cec323_cover_image.jpg/600x600bb.jpg"},
                    {"The Vanishing Witness", "Clara Hunt", "Mystery", "https://is1-ssl.mzstatic.com/image/thumb/Publication123/v4/87/ab/75/87ab7571-075d-f9fe-306c-8e6ec90a6dfc/9781984806451.d.jpg/600x600bb.jpg"},
                    {"A Trace of Blood", "Samuel Grant", "Thriller", "https://is1-ssl.mzstatic.com/image/thumb/Publication221/v4/04/4f/aa/044faae0-c360-3472-b4c4-976c9fdf27af/0000050038.jpg/600x600bb.jpg"},
                    {"The Locked Corridor", "Natalie Stone", "Mystery", "https://is1-ssl.mzstatic.com/image/thumb/Publication221/v4/8f/f0/37/8ff03708-cd47-2bfd-4f63-80effa9dfc5b/9786178880514.jpg/600x600bb.jpg"},
                    {"Behind Closed Doors", "Hannah Brooks", "Thriller", "https://is1-ssl.mzstatic.com/image/thumb/Publication124/v4/f5/f3/60/f5f360c7-97a4-ebc2-443c-0bcef1f855b5/9781250121011.jpg/600x600bb.jpg"},
                    {"The Last Suspect", "Ryan Foster", "Thriller", "https://is1-ssl.mzstatic.com/image/thumb/Publication221/v4/a0/90/3a/a0903a38-76a5-f3ad-fb50-48704f7952a6/9780008364830.jpg/600x600bb.jpg"},
                    {"Shadows in the Alley", "Grace Morgan", "Mystery", "https://is1-ssl.mzstatic.com/image/thumb/Publication211/v4/08/81/38/088138f6-311d-ee1a-0330-5333137f7f06/1063041618.jpg/600x600bb.jpg"},
                    {"The Final Clue", "Ethan Walker", "Mystery", "https://is1-ssl.mzstatic.com/image/thumb/Publication211/v4/04/48/bd/0448bd84-4702-b7ec-3631-9776fe7b3466/6610001017156.jpg/600x600bb.jpg"},
                    {"Dead Silence", "Victoria James", "Thriller", "https://is1-ssl.mzstatic.com/image/thumb/Publication/61/3a/23/mzi.txkzqzhi.jpg/600x600bb.jpg"},
                    {"The Dark Evidence", "Benjamin Scott", "Thriller", "https://is1-ssl.mzstatic.com/image/thumb/Publication221/v4/8c/76/89/8c76890d-737b-4235-e724-6e900f5c3f90/dd01e059-2100-4d30-a737-e07f703960fd_cover_image.jpg/600x600bb.jpg"},
                    {"Beyond the Andromeda Gate", "Lucas King", "Science Fiction", "https://is1-ssl.mzstatic.com/image/thumb/Publication128/v4/50/d6/75/50d6752a-a0e4-25c3-2628-0b2e77cd0813/9781370670642.jpg/600x600bb.jpg"},
                    {"Mars Colony Rising", "Evelyn Ross", "Science Fiction", "https://is1-ssl.mzstatic.com/image/thumb/Publication221/v4/d4/44/04/d444046c-2025-aedd-0177-fa941c919c1c/cd1a45ea-a4d3-4e1c-8102-b6a579b32e12_cover_image.jpg/600x600bb.jpg"},
                    {"The Quantum Horizon", "Nathan Blake", "Science Fiction", "https://is1-ssl.mzstatic.com/image/thumb/Publication221/v4/ac/46/28/ac4628c2-b172-3b37-398e-ba0d8b89e729/0002594879.jpg/600x600bb.jpg"},
                    {"Echoes from Titan", "Sarah Mitchell", "Science Fiction", "https://is1-ssl.mzstatic.com/image/thumb/Publication122/v4/1d/a5/06/1da506ee-f300-33d5-c865-b3b72c206de8/9781416578956.jpg/600x600bb.jpg"},
                    {"The Last Human Protocol", "Jason Hill", "Science Fiction", "https://is1-ssl.mzstatic.com/image/thumb/Publication113/v4/89/3c/f0/893cf0e8-444c-307b-044b-ea92c3164d53/9781625797100.jpg/600x600bb.jpg"},
                    {"Cybernetic Dawn", "Mia Turner", "Science Fiction", "https://is1-ssl.mzstatic.com/image/thumb/Publication124/v4/61/da/8a/61da8ae3-d8c5-b234-7dc9-5ab320c28417/0000657264.jpg/600x600bb.jpg"},
                    {"Orbit of Shadows", "Caleb Reed", "Science Fiction", "https://is1-ssl.mzstatic.com/image/thumb/Publication112/v4/b1/a4/73/b1a47362-30f4-168e-b47d-8b1b67030e4c/9780316040228.jpg/600x600bb.jpg"},
                    {"The Galactic Cipher", "Zoe Brooks", "Science Fiction", "https://is1-ssl.mzstatic.com/image/thumb/Publication221/v4/9c/02/50/9c025095-7201-2922-d924-912afbdf13fa/0002790032.jpg/600x600bb.jpg"},
                    {"Planet Zero", "Levi Cooper", "Science Fiction", "https://is1-ssl.mzstatic.com/image/thumb/Publication118/v4/6a/e1/18/6ae1185e-b537-716e-acfa-f5bcb27f61de/0000318145.jpg/600x600bb.jpg"},
                    {"The Time Code", "Lily Parker", "Science Fiction", "https://is1-ssl.mzstatic.com/image/thumb/Publication/v4/88/b3/de/88b3de90-3b26-63dd-a205-b518d87b71a0/9781434448170.jpg/600x600bb.jpg"},
                    {"The Dragon’s Crown", "Eliana Frost", "Fantasy", "https://is1-ssl.mzstatic.com/image/thumb/Publication221/v4/6a/f1/56/6af156b9-b805-c0e9-576e-775f517c3cc3/0001777729.jpg/600x600bb.jpg"},
                    {"Kingdom of Ashes", "Rowan Blake", "Fantasy", "https://is1-ssl.mzstatic.com/image/thumb/Publication211/v4/9b/89/27/9b8927f0-9bc4-7282-27fa-e3bd49dd0b42/0004200459.jpg/600x600bb.jpg"},
                    {"The Moonstone Prophecy", "Hazel Quinn", "Fantasy", "https://is1-ssl.mzstatic.com/image/thumb/Publication221/v4/1f/91/33/1f9133be-a8ab-0434-ca5c-30a9f4cf4ea3/6610000750139.jpg/600x600bb.jpg"},
                    {"Sword of the Fallen King", "Jasper Reid", "Fantasy", "https://is1-ssl.mzstatic.com/image/thumb/Publication126/v4/0d/5e/c4/0d5ec43a-6c12-a7a4-c21c-f03e127700c8/9781094382227.jpg/600x600bb.jpg"},
                    {"The Enchanted Forest", "Freya Lane", "Fantasy", "https://is1-ssl.mzstatic.com/image/thumb/Publication211/v4/fa/7a/c3/fa7ac330-c401-17af-99dc-8750f55fae2f/0000750497.jpg/600x600bb.jpg"},
                    {"Shadow of the Phoenix", "Asher Cole", "Fantasy", "https://is1-ssl.mzstatic.com/image/thumb/Publication125/v4/b1/de/7c/b1de7c8b-1eae-c017-ad6a-a0060be7d7f8/9781635551822.jpg/600x600bb.jpg"},
                    {"The Crystal Throne", "Iris Bell", "Fantasy", "https://is1-ssl.mzstatic.com/image/thumb/Publication/v4/db/26/bd/db26bd0d-4a57-61c1-23a1-a17383496669/9781620955291.jpg/600x600bb.jpg"},
                    {"The Witch of Evermoor", "Nora West", "Fantasy", "https://is1-ssl.mzstatic.com/image/thumb/Publication122/v4/aa/6a/57/aa6a57ce-67ae-1f89-105d-019314871b48/0000019937.jpg/600x600bb.jpg"},
                    {"Flames of Valoria", "Finn Carter", "Fantasy", "https://is1-ssl.mzstatic.com/image/thumb/Publication211/v4/8f/6d/ea/8f6deaed-6993-6fc5-3f01-9709d64188d3/6610000678525.jpg/600x600bb.jpg"},
                    {"The Hidden Realm", "Violet Hayes", "Fantasy", "https://is1-ssl.mzstatic.com/image/thumb/Publication4/v4/2f/6f/76/2f6f76f7-a0d2-77a0-fd78-57eb123b6320/9781310229756.jpg/600x600bb.jpg"},
                    {"Love in Paris Rain", "Amelia Stone", "Romance", "https://is1-ssl.mzstatic.com/image/thumb/Publication/v4/4e/a3/d8/4ea3d8c8-e976-8935-648a-2d1d92245bc4/9780679604440.jpg/600x600bb.jpg"},
                    {"A Summer to Remember", "Ethan Hayes", "Romance", "https://is1-ssl.mzstatic.com/image/thumb/Publication211/v4/75/93/02/75930210-6bcc-dd6a-2d5e-59a342cdb71a/asummertoremember-300dpi.jpg/600x600bb.jpg"},
                    {"Hearts in Bloom", "Lily Morgan", "Romance", "https://is1-ssl.mzstatic.com/image/thumb/Publication211/v4/fb/a1/6b/fba16b83-d1ee-7bcd-a05e-0a76c57f019b/1058308097.jpg/600x600bb.jpg"},
                    {"Forever Starts Here", "Chloe Adams", "Romance", "https://is1-ssl.mzstatic.com/image/thumb/Publication116/v4/34/01/92/34019254-e611-9413-e0c8-d48cfd5fd0cc/0000296535.jpg/600x600bb.jpg"},
                    {"The Art of Us", "Gabriel Reed", "Romance", "https://is1-ssl.mzstatic.com/image/thumb/Publication122/v4/80/aa/8e/80aa8edc-58aa-768a-1059-243110f04fa2/TheArtofUs-300dpi.jpg/600x600bb.jpg"},
                    {"When We Met Again", "Mia Collins", "Romance", "https://is1-ssl.mzstatic.com/image/thumb/Publication211/v4/e6/6d/45/e66d4530-c02f-3e6d-bbeb-8a1cb99dfbf7/0004064534.jpg/600x600bb.jpg"},
                    {"Letters to My Heart", "Noah Parker", "Romance", "https://is1-ssl.mzstatic.com/image/thumb/Publication128/v4/16/bd/8b/16bd8bf5-597a-1d67-d3f8-fd25595a8527/9781416591368.jpg/600x600bb.jpg"},
                    {"The Perfect Sunset", "Ava Brooks", "Romance", "https://picsum.photos/id/148/400/600"},
                    {"Falling for You", "Daniel White", "Romance", "https://picsum.photos/id/149/400/600"},
                    {"Our Last First Date", "Sophie Hall", "Romance", "https://picsum.photos/id/150/400/600"},
                    {"The Queen’s Messenger", "Margaret Stone", "Historical Fiction", "https://is1-ssl.mzstatic.com/image/thumb/Publication221/v4/f1/e5/9c/f1e59c94-058d-bf56-b84f-62a0d977fa95/9781416593973.jpg/600x600bb.jpg"},
                    {"War and Silk", "Henry Collins", "Historical Fiction", "https://picsum.photos/id/152/400/600"},
                    {"The Paris Resistance", "Eleanor Hart", "Historical Fiction", "https://is1-ssl.mzstatic.com/image/thumb/Publication116/v4/2b/54/2d/2b542d76-6843-7506-6524-cd3e0aed2d52/9781982134211.jpg/600x600bb.jpg"},
                    {"Beneath the Roman Sun", "Julian Frost", "Historical Fiction", "https://is1-ssl.mzstatic.com/image/thumb/Publication20/v4/51/9e/a3/519ea3c6-ac11-72a2-6fbc-4aaeede076ca/9780007568857_marketingimage.jpg/600x600bb.jpg"},
                    {"The Last Samurai’s Daughter", "Naomi Reed", "Historical Fiction", "https://is1-ssl.mzstatic.com/image/thumb/Publication124/v4/b8/64/67/b864672d-ab2f-eccc-7384-7af9ed380657/9781948924313.jpg/600x600bb.jpg"},
                    {"Letters from the Battlefield", "Thomas Walker", "Historical Fiction", "https://is1-ssl.mzstatic.com/image/thumb/Publication118/v4/18/ce/f9/18cef9a3-3bbc-5c64-5be3-571fd353db7f/9780870208522-frontcover.jpg/600x600bb.jpg"},
                    {"A Crown in Winter", "Beatrice Scott", "Historical Fiction", "https://is1-ssl.mzstatic.com/image/thumb/Publication221/v4/3b/aa/2e/3baa2ee9-f227-c897-eeb1-30b22432dfcc/9781496749116.jpg/600x600bb.jpg"},
                    {"The Florence Painter", "Lucia Hayes", "Historical Fiction", "https://picsum.photos/id/158/400/600"},
                    {"Echoes of Empire", "William Parker", "Historical Fiction", "https://picsum.photos/id/159/400/600"},
                    {"The Lost Heir of Vienna", "Clara West", "Historical Fiction", "https://picsum.photos/id/160/400/600"},
                    {"My Journey Through Time", "Aaron Lewis", "Biography", "https://is1-ssl.mzstatic.com/image/thumb/Publication126/v4/d8/3c/ee/d83cee78-320f-d36c-1b90-6551702b2f82/8ae56293-315c-40c3-95b6-146cfdb0e588_cover_image.jpg/600x600bb.jpg"},
                    {"The Life I Chose", "Rachel Green", "Autobiography", "https://is1-ssl.mzstatic.com/image/thumb/Publication116/v4/f9/9b/f8/f99bf894-933c-88bf-9dce-a4f6c00f4437/9783748794332.jpg/600x600bb.jpg"},
                    {"A Voice for Change", "Priya Mehta", "Biography", "https://is1-ssl.mzstatic.com/image/thumb/Publication71/v4/5c/02/12/5c021251-e65f-78c5-2857-822390eb0792/0000188108.jpg/600x600bb.jpg"},
                    {"Climbing My Own Mountain", "Vikram Shah", "Autobiography", "https://is1-ssl.mzstatic.com/image/thumb/Publication6/v4/50/23/07/50230792-eba2-a273-be47-93753ad27adb/9780743214384.jpg/600x600bb.jpg"},
                    {"Against All Odds", "Michael Brown", "Biography", "https://is1-ssl.mzstatic.com/image/thumb/Publication112/v4/08/ea/19/08ea19ab-1e54-85f2-084d-10e5afb98d09/9780593183762.d.jpg/600x600bb.jpg"},
                    {"From Dust to Dreams", "Neha Kapoor", "Autobiography", "https://is1-ssl.mzstatic.com/image/thumb/Publication/4a/81/b7/mzi.afuekzhv.jpg/600x600bb.jpg"},
                    {"The Road I Walked", "Daniel King", "Biography", "https://picsum.photos/id/167/400/600"},
                    {"A Story Untold", "Simran Kaur", "Autobiography", "https://picsum.photos/id/168/400/600"},
                    {"Living with Purpose", "Jonathan Reed", "Biography", "https://picsum.photos/id/169/400/600"},
                    {"The Strength Within", "Kavya Sinha", "Biography", "https://picsum.photos/id/170/400/600"},
                    {"Master Your Mind", "Rohan Mehta", "Self-Help", "https://picsum.photos/id/171/400/600"},
                    {"The Power of Tiny Habits", "Claire Adams", "Self-Help", "https://picsum.photos/id/172/400/600"},
                    {"Rise Every Day", "Nikhil Verma", "Motivation", "https://is1-ssl.mzstatic.com/image/thumb/Publication/ef/68/5d/mzi.cuennkpb.jpg/600x600bb.jpg"},
                    {"Unstoppable Focus", "Arjun Batra", "Self-Help", "https://picsum.photos/id/174/400/600"},
                    {"Build Better Discipline", "Meera Joshi", "Self-Help", "https://picsum.photos/id/175/400/600"},
                    {"Win the Morning", "David Ross", "Motivation", "https://picsum.photos/id/176/400/600"},
                    {"The Confidence Formula", "Sana Khan", "Self-Help", "https://picsum.photos/id/177/400/600"},
                    {"Mindset for Success", "Ravi Kapoor", "Motivation", "https://picsum.photos/id/178/400/600"},
                    {"Fearless Growth", "Jessica Turner", "Self-Help", "https://picsum.photos/id/179/400/600"},
                    {"The Calm Within", "Ananya Sen", "Self-Help", "https://picsum.photos/id/180/400/600"},
                    {"Startup Blueprint", "Kevin Morris", "Business", "https://picsum.photos/id/181/400/600"},
                    {"The Founder’s Mindset", "Aditya Jain", "Entrepreneurship", "https://picsum.photos/id/182/400/600"},
                    {"Profit with Purpose", "Rachel Young", "Business", "https://picsum.photos/id/183/400/600"},
                    {"Build, Scale, Lead", "Karan Malhotra", "Entrepreneurship", "https://picsum.photos/id/184/400/600"},
                    {"Smart Money Moves", "Alisha Grant", "Finance", "https://picsum.photos/id/185/400/600"},
                    {"The Growth Company", "Vivek Nanda", "Business", "https://picsum.photos/id/186/400/600"},
                    {"Marketing That Converts", "Tara Blake", "Marketing", "https://picsum.photos/id/187/400/600"},
                    {"Zero to Brand", "Ishaan Kapoor", "Entrepreneurship", "https://picsum.photos/id/188/400/600"},
                    {"The Art of Negotiation", "Helen Price", "Business", "https://picsum.photos/id/189/400/600"},
                    {"Leaders Create Leaders", "Sunil Arora", "Leadership", "https://picsum.photos/id/190/400/600"},
                    {"Introduction to C Programming", "Raj Malhotra", "Programming", "https://picsum.photos/id/191/400/600"},
                    {"Mastering C++ Fundamentals", "Nitin Verma", "Programming", "https://picsum.photos/id/192/400/600"},
                    {"Java for Modern Developers", "Ankit Sharma", "Programming", "https://picsum.photos/id/193/400/600"},
                    {"Python Essentials", "Sneha Gupta", "Programming", "https://picsum.photos/id/194/400/600"},
                    {"Data Structures Made Easy", "Kunal Arora", "Computer Science", "https://picsum.photos/id/195/400/600"},
                    {"Algorithms Simplified", "Pooja Bansal", "Computer Science", "https://picsum.photos/id/196/400/600"},
                    {"Database Management Systems", "Rahul Sethi", "DBMS", "https://picsum.photos/id/197/400/600"},
                    {"Operating Systems Explained", "Mohit Kapoor", "Computer Science", "https://picsum.photos/id/198/400/600"},
                    {"Computer Networks Basics", "Tushar Bhatia", "Computer Science", "https://picsum.photos/id/199/400/600"},
                    {"Web Development with HTML CSS and JavaScript", "Simran Arora", "Web Development", "https://picsum.photos/id/200/400/600"},
                    {"Artificial Intelligence Basics", "Aditi Khanna", "AI", "https://picsum.photos/id/201/400/600"},
                    {"Machine Learning Foundations", "Harsh Mehta", "Machine Learning", "https://picsum.photos/id/202/400/600"},
                    {"Deep Learning in Practice", "Keshav Rao", "Deep Learning", "https://picsum.photos/id/203/400/600"},
                    {"Data Science with Python", "Ritu Sharma", "Data Science", "https://picsum.photos/id/204/400/600"},
                    {"Neural Networks Explained", "Aman Bedi", "AI", "https://picsum.photos/id/205/400/600"},
                    {"Practical NLP", "Sakshi Jain", "AI", "https://picsum.photos/id/206/400/600"},
                    {"Statistics for Data Analysis", "Vivek Shah", "Data Science", "https://picsum.photos/id/207/400/600"},
                    {"Computer Vision Essentials", "Isha Verma", "AI", "https://picsum.photos/id/208/400/600"},
                    {"AI for Beginners", "Rohit Sinha", "AI", "https://picsum.photos/id/209/400/600"},
                    {"Predictive Analytics Handbook", "Megha Kapoor", "Data Science", "https://picsum.photos/id/210/400/600"},
                    {"Foundations of Cybersecurity", "Arnav Khurana", "Cybersecurity", "https://picsum.photos/id/211/400/600"},
                    {"Ethical Hacking Essentials", "Deepak Arora", "Cybersecurity", "https://picsum.photos/id/212/400/600"},
                    {"Network Security Principles", "Pritam Das", "Cybersecurity", "https://picsum.photos/id/213/400/600"},
                    {"Cryptography Simplified", "Naman Kapoor", "Cybersecurity", "https://picsum.photos/id/214/400/600"},
                    {"Web Security Attacks and Defense", "Sahil Mehta", "Cybersecurity", "https://picsum.photos/id/215/400/600"},
                    {"Digital Forensics Basics", "Kunal Saxena", "Cybersecurity", "https://picsum.photos/id/216/400/600"},
                    {"Secure Coding Practices", "Garima Singh", "Cybersecurity", "https://picsum.photos/id/217/400/600"},
                    {"Malware Analysis Fundamentals", "Dev Sharma", "Cybersecurity", "https://picsum.photos/id/218/400/600"},
                    {"Identity and Access Control", "Ritika Jain", "Cybersecurity", "https://picsum.photos/id/219/400/600"},
                    {"Cloud Security Guide", "Anmol Gupta", "Cybersecurity", "https://picsum.photos/id/220/400/600"},
                    {"Calculus for Beginners", "Rohit Bansal", "Mathematics", "https://picsum.photos/id/221/400/600"},
                    {"Linear Algebra Essentials", "Devansh Kapoor", "Mathematics", "https://picsum.photos/id/222/400/600"},
                    {"Discrete Mathematics Simplified", "Priyansh Verma", "Mathematics", "https://picsum.photos/id/223/400/600"},
                    {"Probability and Statistics", "Aakash Suri", "Mathematics", "https://picsum.photos/id/224/400/600"},
                    {"Number Theory Basics", "Tanvi Gupta", "Mathematics", "https://picsum.photos/id/225/400/600"},
                    {"Trigonometry Made Easy", "Nidhi Sinha", "Mathematics", "https://picsum.photos/id/226/400/600"},
                    {"Mathematical Reasoning", "Harleen Kaur", "Mathematics", "https://picsum.photos/id/227/400/600"},
                    {"Advanced Algebra Concepts", "Sahil Khanna", "Mathematics", "https://picsum.photos/id/228/400/600"},
                    {"Differential Equations Intro", "Kriti Arora", "Mathematics", "https://picsum.photos/id/229/400/600"},
                    {"Geometry and Proofs", "Arpit Jain", "Mathematics", "https://picsum.photos/id/230/400/600"},
                    {"Fundamentals of Mechanics", "Aviral Sharma", "Physics", "https://picsum.photos/id/231/400/600"},
                    {"Waves and Oscillations", "Neeraj Bhatia", "Physics", "https://picsum.photos/id/232/400/600"},
                    {"Electricity and Magnetism", "Pankaj Arora", "Physics", "https://picsum.photos/id/233/400/600"},
                    {"Modern Physics Essentials", "Raghav Sethi", "Physics", "https://picsum.photos/id/234/400/600"},
                    {"Quantum Physics for Beginners", "Sanya Kapoor", "Physics", "https://picsum.photos/id/235/400/600"},
                    {"Thermodynamics Explained", "Laksh Mehra", "Physics", "https://picsum.photos/id/236/400/600"},
                    {"Optics and Light", "Keshav Sharma", "Physics", "https://picsum.photos/id/237/400/600"},
                    {"Solid State Physics Basics", "Ayush Gupta", "Physics", "https://picsum.photos/id/238/400/600"},
                    {"Semiconductor Physics", "Bhavya Jain", "Physics", "https://picsum.photos/id/239/400/600"},
                    {"Astrophysics Introduction", "Tania Malhotra", "Physics", "https://picsum.photos/id/240/400/600"},
                    {"Organic Chemistry Basics", "Ishita Sharma", "Chemistry", "https://picsum.photos/id/241/400/600"},
                    {"Inorganic Chemistry Concepts", "Prateek Sinha", "Chemistry", "https://picsum.photos/id/242/400/600"},
                    {"Physical Chemistry Fundamentals", "Vaibhav Arora", "Chemistry", "https://picsum.photos/id/243/400/600"},
                    {"Analytical Chemistry Guide", "Muskan Verma", "Chemistry", "https://picsum.photos/id/244/400/600"},
                    {"Biochemistry Essentials", "Akash Bedi", "Chemistry", "https://picsum.photos/id/245/400/600"},
                    {"Environmental Chemistry", "Kritika Jain", "Chemistry", "https://picsum.photos/id/246/400/600"},
                    {"Industrial Chemistry", "Saurabh Kapoor", "Chemistry", "https://picsum.photos/id/247/400/600"},
                    {"Polymer Chemistry Basics", "Hina Khan", "Chemistry", "https://picsum.photos/id/248/400/600"},
                    {"Electrochemistry Explained", "Rajeev Malhotra", "Chemistry", "https://picsum.photos/id/249/400/600"},
                    {"Surface Chemistry Intro", "Neha Sethi", "Chemistry", "https://picsum.photos/id/250/400/600"},
                    {"Cell Biology Fundamentals", "Tanya Mehta", "Biology", "https://picsum.photos/id/251/400/600"},
                    {"Genetics and Evolution", "Yash Verma", "Biology", "https://picsum.photos/id/252/400/600"},
                    {"Human Anatomy Basics", "Kriti Sharma", "Biology", "https://picsum.photos/id/253/400/600"},
                    {"Microbiology Essentials", "Adarsh Nanda", "Biology", "https://picsum.photos/id/254/400/600"},
                    {"Biotechnology Today", "Preeti Arora", "Biology", "https://picsum.photos/id/255/400/600"},
                    {"Ecology and Environment", "Gauri Khanna", "Biology", "https://picsum.photos/id/256/400/600"},
                    {"Plant Physiology", "Ritesh Jain", "Biology", "https://picsum.photos/id/257/400/600"},
                    {"Molecular Biology Intro", "Ishaan Mehra", "Biology", "https://picsum.photos/id/258/400/600"},
                    {"Zoology Simplified", "Poonam Bhatia", "Biology", "https://picsum.photos/id/259/400/600"},
                    {"Immunology Basics", "Srishti Kapoor", "Biology", "https://picsum.photos/id/260/400/600"},
                    {"Ancient Civilizations of the World", "Ramesh Tiwari", "History", "https://picsum.photos/id/261/400/600"},
                    {"Medieval India", "Sunita Rao", "History", "https://picsum.photos/id/262/400/600"},
                    {"The Freedom Movement", "Arvind Joshi", "History", "https://picsum.photos/id/263/400/600"},
                    {"World War Chronicles", "Daniel Scott", "History", "https://picsum.photos/id/264/400/600"},
                    {"The Rise of Empires", "Hemant Sharma", "History", "https://picsum.photos/id/265/400/600"},
                    {"History of Europe", "Clara Benson", "History", "https://picsum.photos/id/266/400/600"},
                    {"The Mughal Era", "Zoya Khan", "History", "https://picsum.photos/id/267/400/600"},
                    {"Revolutions That Changed the World", "Mehul Verma", "History", "https://picsum.photos/id/268/400/600"},
                    {"Colonialism and Resistance", "Anita Das", "History", "https://picsum.photos/id/269/400/600"},
                    {"A Brief History of Modern India", "Vivek Anand", "History", "https://picsum.photos/id/270/400/600"},
                    {"Physical Geography Basics", "Ashutosh Mehta", "Geography", "https://picsum.photos/id/271/400/600"},
                    {"Human Geography Explained", "Saket Sharma", "Geography", "https://picsum.photos/id/272/400/600"},
                    {"Climatology and Weather", "Riya Sethi", "Geography", "https://picsum.photos/id/273/400/600"},
                    {"Geomorphology Fundamentals", "Nitin Khanna", "Geography", "https://picsum.photos/id/274/400/600"},
                    {"Economic Geography", "Neelam Gupta", "Geography", "https://picsum.photos/id/275/400/600"},
                    {"Geography of India", "Rahul Menon", "Geography", "https://picsum.photos/id/276/400/600"},
                    {"World Atlas and Regions", "Piyush Arora", "Geography", "https://picsum.photos/id/277/400/600"},
                    {"Environmental Geography", "Mehak Jain", "Geography", "https://picsum.photos/id/278/400/600"},
                    {"Population Geography", "Shweta Kapoor", "Geography", "https://picsum.photos/id/279/400/600"},
                    {"Oceanography Basics", "Tanay Bedi", "Geography", "https://picsum.photos/id/280/400/600"},
                    {"The Thinking Mind", "Aarav Sinha", "Psychology", "https://picsum.photos/id/281/400/600"},
                    {"Introduction to Philosophy", "Meenal Kapoor", "Philosophy", "https://picsum.photos/id/282/400/600"},
                    {"Human Behavior Explained", "Priyanka Das", "Psychology", "https://picsum.photos/id/283/400/600"},
                    {"Cognitive Psychology Basics", "Shubham Jain", "Psychology", "https://picsum.photos/id/284/400/600"},
                    {"Ethics and Society", "Manav Sharma", "Philosophy", "https://picsum.photos/id/285/400/600"},
                    {"Theories of Personality", "Anjali Mehta", "Psychology", "https://picsum.photos/id/286/400/600"},
                    {"Logic and Critical Thinking", "Rohit Nanda", "Philosophy", "https://picsum.photos/id/287/400/600"},
                    {"Social Psychology Simplified", "Kavita Verma", "Psychology", "https://picsum.photos/id/288/400/600"},
                    {"Philosophy of Life", "Devika Rao", "Philosophy", "https://picsum.photos/id/289/400/600"},
                    {"Emotional Intelligence and You", "Kiran Malhotra", "Psychology", "https://picsum.photos/id/290/400/600"},
                    {"Wonders of the World", "Harshita Jain", "Travel", "https://picsum.photos/id/291/400/600"},
                    {"Backpacking Across Europe", "Neil Dsouza", "Travel", "https://picsum.photos/id/292/400/600"},
                    {"Hidden Places of India", "Saumya Kapoor", "Travel", "https://picsum.photos/id/293/400/600"},
                    {"Mountains, Rivers and Roads", "Arjun Sen", "Travel", "https://picsum.photos/id/294/400/600"},
                    {"The Explorer’s Journal", "Rohan Das", "Travel", "https://picsum.photos/id/295/400/600"},
                    {"World Facts Encyclopedia", "Deepa Malhotra", "General Knowledge", "https://picsum.photos/id/296/400/600"},
                    {"Amazing Inventions", "Ritesh Verma", "General Knowledge", "https://picsum.photos/id/297/400/600"},
                    {"Great Scientists of the World", "Naina Sharma", "General Knowledge", "https://picsum.photos/id/298/400/600"},
                    {"Countries and Capitals Handbook", "Harshit Batra", "General Knowledge", "https://picsum.photos/id/299/400/600"},
                    {"Knowledge Booster 101", "Tanisha Gupta", "General Knowledge", "https://picsum.photos/id/300/400/600"},
                };

                int isbnCounter = 1000;
                for (Object[] data : bookData) {
                    Book b = new Book();
                    b.setTitle((String) data[0]);
                    b.setAuthor((String) data[1]);
                    
                    int currentIsbn = isbnCounter++;
                    b.setIsbn("ISBN-" + currentIsbn);
                    b.setCategory((String) data[2]);
                    b.setCoverUrl((String) data[3]);
                    
                    b.setTotalCopies(5);
                    b.setAvailableCopies(5);
                    bookRepository.save(b);
                }
            }
        };
    }
}
