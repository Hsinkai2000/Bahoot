create database if not exists bahoot;

use bahoot;
 
drop table if exists responses;
drop table if exists Users;
drop table if exists questions;
drop table if exists question_sets;
drop table if exists session;
drop table if exists score;

create table question_sets (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(50),
    totalQn int,
    ownerID int,
    primary key(id)
);

create table questions (
    id int NOT NULL AUTO_INCREMENT,
    setID int,
    qnNo int,
    question varchar(150),
    opt1 varchar(100),
    opt2 varchar(100),
    opt3 varchar(100),
    opt4 varchar(100),
    correctOpt int,
    primary key(id)
);

create table Users (
    id int NOT NULL AUTO_INCREMENT,
    email varchar(50),
    name varchar(50),
    password varchar(50),
    mobile_number int,
    primary key (id)
);

Create table responses (
    id int NOT NULL AUTO_INCREMENT,
    room_code varchar(6),
    questionSetID int,
    questionNo int,
    choice int,
    respondee varchar(50),
    userID int,
    result varchar(50),
    comment varchar(500),
    primary key (id)
);

Create table session (
    id int NOT NULL AUTO_INCREMENT,
    room_code varchar(6) UNIQUE,
    current_question_id int,
    primary key (id)
);

Create table score (
    id int NOT NULL AUTO_INCREMENT,
    room_code varchar(6),
    userID int,
    name varchar(50),
    score int,
    primary key (id)
);

INSERT INTO questions (setID, qnNo, question, opt1, opt2, opt3, opt4, correctOpt ) VALUES
	('1', '1', 'If a super class has a total of 10 instances, what is the total number of instances of subclasses can there be?', '3', '6', '10', '14', '3 '),
	('1', '2', 'Constructors are used to', 'Build a user interface', 'Free Memory', 'Initialize a newly created object', 'To create a subclass', '3 '),
	('1', '3', 'An object that has more than one form is referred to as:', 'inheritance', 'interface', 'Abstract class', 'Polymorphism', '4 '),
	('1', '4', ' In which access should a constructor be defined so that object of the class can be created in any function?', 'Any access specifier will work', 'Public', ' Protected', 'Private', '2 '),
	('1', '5', 'Which access specifier is usually used for data members of a class?', 'Private', 'Protected', 'Public', 'Default', '1 '),
	('2', '1', 'Choose the correct HTML element for the largest heading:', '<h6>', '<h1>', '<heading>', '<head>', '2 '),
	('2', '2', 'Choose the correct HTML element to define important text', '<b>', '<i>', '<important>', '<strong>', '4 '),
	('2', '3', 'Which HTML attribute specifies an alternate text for an image if the image cannot be displayed?', 'alt', 'src', 'title', 'longdesc', '1 '),
	('3', '1', 'Which of the following code is used to get an attribute in a HTTP Session object in servlets?', 'session.alterAttribute(String name)', 'session.getAttribute(String name)', 'session.updateAttribute(String name)', 'session.setAttribute(String name)', '2 '),
	('3', '2', 'Which method is used to get three-letter abbreviation for locale’s country in servlets?', ' Locale.getISO3Country()', 'Response.getISO3Country()', 'Request.getISO3Country()', ' Local.retrieveISO3Country()', '3 '),
	('3', '3', 'Which of the following code retrieves the body of the request as binary data?', 'DataInputStream data = new InputStream()', 'DataInputStream data = response.getInputStream()', 'DataInputStream data = request.fetchInputStream()', 'DataInputStream data = request.getInputStream()', '4 '),
	('4', '1', 'Which of the following does not supports JSP directly?', 'Weblogic Server', 'Tomcat Server', 'Apache HTTP Server', 'WebSphere Server', '3 '),
	('4', '2', 'Which of the following passes information from JSP to included JSP?', '<%jsp:param>', '<%jsp:page>', '<%jsp:import>', '<%jsp:useBean>', '1 '),
	('4', '3', 'Which of the following attributes can be used to generate a PDF page?', 'contentType', 'generatePdf', 'typePDF', 'contentPDF', '1 '),
	('5', '1', 'A discrete random variable can take on which of the following types of values?', 'Only non-integer values', 'Only integer values', 'Both integer and non-integer values', 'None of the above', '2 '),
	('5', '2', 'What is the formula for the z-score of a value in a dataset?', '(mean - value) / standard deviation', '(value + mean) / standard deviation', '(mean - value) * standard deviation', '(value - mean) / standard deviation', '4 '),
	('5', '3', 'The probability of an event occurring is always which of the following?', 'A value greater than 1', 'A value between 0 and 1', 'A value less than 0', 'None of the above', '2 '),
	('5', '4', 'In a normal distribution, what percentage of the data falls within one standard deviation of the mean?', 'Approximately 99%', 'Approximately 95%', 'Approximately 68%', 'None of the above', '3 '),
	('5', '5', 'What is the z-score of a value of 28 in a dataset with a mean of 20 and a standard deviation of 4?', '2', '4', '8', '20', '1'),
    ('6', '1', 'What is the syntax for declaring a public variable in a C# script in Unity?', 'public type variableName;', 'public variableName;', 'variableName public;', 'type variableName public;', '1 '),
    ('6', '2', 'Which method in Unity is called once per frame and is often used for updating the game state?', 'Start()', 'FixedUpdate()', 'Update()', 'LateUpdate()', '3 '),
	('6', '3', 'What is the syntax for accessing a component attached to the same GameObject as a script in Unity?', 'this.componentName', 'GameObject.componentName', 'GameObject.GetComponent<componentType>()', 'this.GetComponent<componentType>()', '4 '),
	('6', '4', 'Which method in Unity is called at a fixed interval, and is often used for physics calculations?', 'Start()', 'FixedUpdate()', 'Update()', 'LateUpdate()', '2 '),
	('6', '5', 'What is the purpose of the OnCollisionEnter() method in a Unity script?', 'To detect when a collision occurs between two GameObjects', 'To set the position of a GameObject in the scene', 'To update the score or other game state variables', 'To initialize a component when the script is loaded', '1');

INSERT INTO session (room_code, current_question_id) VALUES
    ("774537",0);

    
INSERT INTO question_sets (name, totalQn, ownerID) VALUES 
    ('Object Oriented Programming', 5, 1),
    ('HTML Programming',3, 1),
    ('Web Servlet Programming',3, 1),    
    ('Web App Programming',3, 1),
    ('Engineering Mathematics 1', 5, 1),
    ('Unity Programming',5, 1);


INSERT INTO Users (email, name, password, mobile_number) VALUES 
	('chockhong@gmail.com', 'Lim Chock Hong', 'passwordlim', 837390484),
	('Jamesliu@gmail.com', 'James Liu', 'passwordliu', 84730572),
	('KellyTay@gmail.com', 'Kelly Tay', 'passwordtay', 94728484),
	('BrandonTan@gmail.com', 'Brandon Tan', 'passwordTan', 93846289),
	('DenonLee@gmail.com', 'Lee Denon', 'passwordLee', 84927943),
	('i@g.com', 'Irfan', '123', 96641005);


INSERT INTO responses (room_code, questionSetID, questionNo, choice, respondee, result, userID, comment) VALUES 
    ("774537", 1, 1, 3, "Lim Chock Hong", "Correct", 1, "Great question, really made me think outside the box."),
    ("774537", 1, 1, 4, "James Liu", "Incorrect", 2, "I appreciated the challenge in this question, it helped me grow my knowledge."),
    ("774537", 1, 1, 3, "Kelly Tay", "Correct", 3, "This question was a great way to test my understanding of the topic."),
    ("774537", 1, 1, 2, "Brandon Tan", "Incorrect", 4, "I enjoyed answering this question, it helped me learn something new."),
    ("774537", 1, 1, 4, "Lee Denon", "Incorrect", 5, "This question really tested my critical thinking skills and I appreciate that."),
    ("774537", 1, 2, 3, "Lim Chock Hong", "Correct", 1, "The wording of this question was clear and concise, making it easy to understand."),
    ("774537", 1, 2, 1, "Kelly Tay", "Incorrect", 3, "I found this question to be well-crafted and thought-provoking."),
    ("774537", 1, 2, 3, "Brandon Tan", "Correct", 4, "This question was a great way to gauge my understanding of the material."),
    ("774537", 1, 2, 2, "Lee Denon", "Incorrect", 5, "I appreciate the opportunity to tackle this challenging question and expand my knowledge."),
    ("774537", 1, 2, 4, "James Liu", "Incorrect", 2 , "This question helped me solidify my understanding of the topic and gave me confidence moving forward."),
    ("774537", 1, 3, 2, "Lim Chock Hong", "Incorrect", 1, "Great question! It really made me think and challenged my knowledge."),
    ("774537", 1, 3, 3, "Kelly Tay", "Incorrect", 3, "I appreciate how this question really tested my understanding of the topic."),
    ("774537", 1, 3, 2, "Brandon Tan", "Incorrect", 4, "This was a well-crafted question that helped me grasp a concept I had been struggling with."),
    ("774537", 1, 4, 2, "Lee Denon", "Correct", 5, "I enjoyed the clarity and precision of this question, it made it easy to understand what was being asked"),
    ("774537", 1, 4, 2, "Kelly Tay", "Correct", 3, "Thanks for including this question - it helped me feel more confident in my understanding of the material."),
    ("774537", 1, 4, 1, "Brandon Tan", "Incorrect", 4, "This question was a good reminder of a concept that I had forgotten about - thank you!"),
    ("774537", 1, 4, 3, "James Liu", "Incorrect", 2, "I appreciated how this question applied the topic to a real-world situation"),
    ("774537", 1, 5, 1, "Lee Denon", "Correct", 5, "Great job! This was an easy one."),
    ("774537", 1, 5, 0, "Kelly Tay", "Correct", 3, "This question really challenged me, but it was satisfying to be able to figure it out in the end."),
    ("774537", 1, 5, 2, "Brandon Tan", "Incorrect", 4, "I found this question to be engaging and interesting, it really kept me on my toes."),
    ("774537", 1, 5, 3, "James Liu", "Incorrect", 2, "Thank you for including this question - it helped me identify an area where I need to study more.");


INSERT INTO score (room_code, userID, name, score) VALUES
    ('774537', 1, "Lim Chock Hong", 2),
    ('774537', 2, "James Liu", 1),
    ('774537', 3, "Kelly Tay", 1),
    ('774537', 4, "Brandon Tan", 1),
    ('774537', 5, "Lee Denon", 1);
    

SELECT * FROM Users;
SELECT * FROM session;
SELECT * FROM score;
SELECT * FROM question_sets;
SELECT * FROM questions;
SELECT * FROM responses;

show tables;
 





    
