package com.example.data.model

object WebDevContent {

    val grade11Lessons = listOf(
        Lesson(
            id = "g11_c1_intro",
            grade = 11,
            chapterNumber = 1,
            chapterTitle = "Introduction to Web Design",
            title = "How the Web Works",
            iconName = "language",
            description = "Understand the basic architecture of the internet, browsers, servers, and the HTTP protocol.",
            durationMin = 15,
            slides = listOf(
                Slide(
                    title = "What is the World Wide Web?",
                    text = "The World Wide Web (WWW) is a system of interlinked hypertext documents accessed via the Internet. It is built on three core pillars: HTML (markup of files), HTTP (the network protocol), and URLs (resource locators).",
                    tip = "Remember: The Internet is the physical network of connected devices; the Web is the collection of pages and files layered on top of it!"
                ),
                Slide(
                    title = "The Client-Server Model",
                    text = "When you browse smartutorial.com, your browser acts as the 'Client'. It requests data from SmartTutorial's computer (the 'Server'). The Server replies with the web page file, which your browser renders.",
                    tip = "Client makes a Request -> Server returns a Response."
                ),
                Slide(
                    title = "Intro to HTML, CSS, and JS",
                    text = "- HTML (HyperText Markup Language) defines the STRUCTURE of a web page.\n- CSS (Cascading Style Sheets) styles the VISUAL LOOK and design.\n- JavaScript adds INTERACTIVE actions.",
                    codeExample = "<!-- HTML Structure -->\n<h1>Welcome to Grade 11 Web!</h1>\n<p>HTML is simple but powerful.</p>",
                    tip = "Think of them as: HTML = Bones, CSS = Clothing/Skin, JavaScript = Muscle Actions."
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "g11_c1_q1",
                    question = "Which technology is responsible for defining the structural content of a web page?",
                    options = listOf("CSS", "HTML", "JavaScript", "PHP"),
                    correctAnswerIndex = 1,
                    explanation = "HTML is HyperText Markup Language, the structural skeleton of any page on the web."
                ),
                QuizQuestion(
                    id = "g11_c1_q2",
                    question = "In the client-server model, what is the role of your web browser?",
                    options = listOf("Server", "DNS Host", "Client", "Database Engine"),
                    correctAnswerIndex = 2,
                    explanation = "The client application (like Chrome or Safari) sends http requests and receives/renders layout content."
                )
            )
        ),
        Lesson(
            id = "g11_c2_html_basics",
            grade = 11,
            chapterNumber = 2,
            chapterTitle = "HTML Fundamentals",
            title = "Tags, Elements & Attributes",
            iconName = "code",
            description = "Master text formatting, structural containers, and head vs body declarations in HTML.",
            durationMin = 20,
            slides = listOf(
                Slide(
                    title = "The Anatomy of a Tag",
                    text = "An HTML element consists of an opening tag, content, and a closing tag. Some elements also have attributes which provide specialized settings inside the opening tag.",
                    codeExample = "<p class=\"highlight\">This is paragraph content</p>\n<!-- <tag attribute=\"value\"> content </tag> -->",
                    tip = "Closing tags always need a forward slash: </tag>."
                ),
                Slide(
                    title = "Primary Document Structure",
                    text = "Every valid HTML document starts with a doctype, followed by structural wrappers. Head contains metadata (title, link to stylesheet), and Body contains what users see.",
                    codeExample = "<!DOCTYPE html>\n<html>\n  <head>\n    <title>Grade 11 Web</title>\n  </head>\n  <body>\n    <p>All visible elements go here!</p>\n  </body>\n</html>",
                    tip = "Keep content in <body>. Content in <head> is processed behind the scenes!"
                ),
                Slide(
                    title = "Text Formatting Tags",
                    text = "Represent distinct textual categories:\n- <h1> to <h6> for Headings (h1 is largest)\n- <p> for paragraphs\n- <strong> for bold emphasis\n- <em> for italicised emphasis\n- <br> for line breaks",
                    codeExample = "<h1>HTML Elements</h1>\n<p>Learning <strong>Web Dev</strong> is <em>fun</em>!</p>",
                    tip = "<br> does not require a closing tag, as it represents an empty/self-closing break element."
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "g11_c2_q1",
                    question = "Which tag is used to create the most important/largest heading on a page?",
                    options = listOf("<h6>", "<h1>", "<head>", "<header>"),
                    correctAnswerIndex = 1,
                    explanation = "<h1> represents the first-level primary section heading."
                ),
                QuizQuestion(
                    id = "g11_c2_q2",
                    question = "Which is a correct example of a self-closing HTML element?",
                    options = listOf("<p>", "<strong>", "<br>", "<html>"),
                    correctAnswerIndex = 2,
                    explanation = "<br> and <img> are self-closing; they do not wrap text so they don't require an explicit closing tag."
                )
            )
        ),
        Lesson(
            id = "g11_c3_links_images",
            grade = 11,
            chapterNumber = 3,
            chapterTitle = "Advanced HTML Elements",
            title = "Links, Images & Tables",
            iconName = "collections",
            description = "Learn how to link documents using anchors, display beautiful images, and present data in responsive HTML tables.",
            durationMin = 25,
            slides = listOf(
                Slide(
                    title = "Supercharging Hyperlinks",
                    text = "Hyperlinks make the Web web-like. They connect pages using anchor tags <a>. Use the href attribute to declare destination URL.",
                    codeExample = "<a href=\"https://smartutorial.com\" target=\"_blank\">\n  Visit SmartTutorial\n</a>",
                    tip = "Using target=\"_blank\" will safely launch the link in a separate tab or web viewer instance."
                ),
                Slide(
                    title = "Inserting Visual Images",
                    text = "Embed photos or graphic vectors using the <img> tag. It requires 'src' (path to file) and 'alt' (alternative textual description for accessibility & screen readers).",
                    codeExample = "<img src=\"images/logo.png\" alt=\"SmartTutorial Logo\" width=\"300\" height=\"100\">",
                    tip = "Alt labels are crucial for Grade 11 exams. Always define content descriptive values!"
                ),
                Slide(
                    title = "Constructing Classic Tables",
                    text = "Tables organize clean linear listings:\n- <table> wrapper\n- <tr> for table row\n- <th> for headers\n- <td> for normal grid cell data",
                    codeExample = "<table>\n  <tr>\n    <th>Subject</th>\n    <th>Resource</th>\n  </tr>\n  <tr>\n    <td>Grade 11 Web</td>\n    <td>SmartTutorial</td>\n  </tr>\n</table>",
                    tip = "Use colspan and rowspan in td tags if cell needs to merge across rows or columns."
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "g11_c3_q1",
                    question = "Which attribute is used inside an anchor tag (<a>) to define the hyperlink's destination?",
                    options = listOf("src", "href", "link", "alt"),
                    correctAnswerIndex = 1,
                    explanation = "href (hypertext reference) sets the target URL address for click interactions."
                ),
                QuizQuestion(
                    id = "g11_c3_q2",
                    question = "What tag indicates a standard column data cell inside an HTML table row?",
                    options = listOf("<tr>", "<th>", "<td>", "<table_cell>"),
                    correctAnswerIndex = 2,
                    explanation = "<td> represents Table Data; it belongs nested inside <tr> nodes."
                )
            )
        ),
        Lesson(
            id = "g11_c4_css_basics",
            grade = 11,
            chapterNumber = 4,
            chapterTitle = "Introduction to CSS",
            title = "Syntax, Selectors & Styles",
            iconName = "style",
            description = "Dress up your bare bones HTML. Cover rules, class, ID selectors, and CSS application rules.",
            durationMin = 22,
            slides = listOf(
                Slide(
                    title = "The CSS Rule Structure",
                    text = "CSS functions on selector-value blocks. A selector identifies target HTML and declares properties in curly brackets.",
                    codeExample = "/* selector { property: value; } */\np {\n  color: #0A58CA;\n  font-size: 16px;\n}",
                    tip = "Always place a semicolon (;) at the end of every property style statement!"
                ),
                Slide(
                    title = "Class vs. ID Selectors",
                    text = "- Class Selectors: Start with a dot (.). Reuse them multiple times on a page.\n- ID Selectors: Start with a hashtag (#). Must be completely unique and used only once per document.",
                    codeExample = "/* Class Rule */\n.main-box {\n  background-color: #F8F9FA;\n}\n\n/* ID Rule */\n#main-header {\n  border: 1px solid;\n}",
                    tip = "Choose class (.name) by default for scalable design rule blocks."
                ),
                Slide(
                    title = "Applying CSS Sheets",
                    text = "Three primary methods:\n1. Inline: declared inside tag attributes.\n2. Internal: declared inside a <style> node in head.\n3. External: linked via external .css files (Best Practice).",
                    codeExample = "<!-- External stylesheet reference -->\n<link rel=\"stylesheet\" href=\"styles.css\">",
                    tip = "External sheets keep code tidy and reuse styling rules among dozens of separate HTML documents."
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "g11_c4_q1",
                    question = "How do you define a class selector in an external CSS file?",
                    options = listOf("#myclass {}", "myclass {}", ".myclass {}", "*myclass {}"),
                    correctAnswerIndex = 2,
                    explanation = "Class selectors are represented with a leading dot (.) in style files."
                ),
                QuizQuestion(
                    id = "g11_c4_q2",
                    question = "Which CSS method is the best practice for styling large projects?",
                    options = listOf("Inline styling", "External stylesheet", "Internal styles inside body", "Using script nodes"),
                    correctAnswerIndex = 1,
                    explanation = "External stylesheets keep layout, logic, and representations completely detached, facilitating scaling."
                )
            )
        )
    )

    val grade12Lessons = listOf(
        Lesson(
            id = "g12_c1_layout",
            grade = 12,
            chapterNumber = 1,
            chapterTitle = "Advanced CSS & Layouts",
            title = "The Box Model & Flexbox",
            iconName = "view_quilt",
            description = "Demystify margin, padding, border configurations and build modern, fluid flexible box structures.",
            durationMin = 25,
            slides = listOf(
                Slide(
                    title = "Understanding the Box Model",
                    text = "Every HTML element is rendered as a box. Inside out:\n- Content: The physical text, canvas, or image.\n- Padding: Clears space around content inside borders.\n- Border: Surrounds padding.\n- Margin: Clears space outside the border boundaries.",
                    codeExample = "div {\n  width: 300px;\n  padding: 20px;\n  border: 2px solid #000;\n  margin: 15px;\n}",
                    tip = "Total width combines: width + paddings + border thickness. Use 'box-sizing: border-box' to change this calculation."
                ),
                Slide(
                    title = "Flexbox Power",
                    text = "Flexbox (Flexible Box Layout) simplifies vertical and horizontal centering. Flex layout responds fluidly to diverse mobile screen widths.",
                    codeExample = ".container {\n  display: flex;\n  justify-content: space-between;\n  align-items: center;\n}",
                    tip = "With justify-content: center and align-items: center, you can perfectly center any component in seconds."
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "g12_c1_q1",
                    question = "Which box property represents the space directly between content and its physical visible border?",
                    options = listOf("Margin", "Padding", "Outline", "Width"),
                    correctAnswerIndex = 1,
                    explanation = "Padding is internal spacing; margin is the external space pushing adjacent sibling components away."
                )
            )
        ),
        Lesson(
            id = "g12_c2_js_intro",
            grade = 12,
            chapterNumber = 2,
            chapterTitle = "JavaScript Fundamentals",
            title = "Variables, Operators & Functions",
            iconName = "reorder",
            description = "Begin client-side scripting. Write your first functional variables, conditions, and logical blocks.",
            durationMin = 30,
            slides = listOf(
                Slide(
                    title = "Scripting on the Browser",
                    text = "JavaScript is a powerful client-side language. Your phone's or computer's browser executes scripts instantly. We connect scripts via <script> tags.",
                    codeExample = "<script>\n  console.log('Hello Ethiopian Web Developers!');\n</script>",
                    tip = "Always check the developer console tool inside browser settings to view standard debug outputs!"
                ),
                Slide(
                    title = "Declaring Web Variables",
                    text = "Store dynamic state data:\n- 'let': declares variable values capable of changing.\n- 'const': declares absolute constant blocks. Never reassigned.",
                    codeExample = "const appHost = \"SmartTutorial\";\nlet studentLessonsCompleted = 4;\n\nstudentLessonsCompleted += 1; // Now value is 5",
                    tip = "Avoid outdated 'var'. Stick to ES6 variables like let and const."
                ),
                Slide(
                    title = "Simple Interactivity",
                    text = "Use functions to encapsulate operations. Tie actions to button clicks from HTML using events like 'onclick'.",
                    codeExample = "function greetUser(studentName) {\n  alert(\"Welcome \" + studentName);\n}\n\n// <button onclick=\"greetUser('Abebe')\">Enter</button>",
                    tip = "Alert boxes are easy to code, although professional pages favor Custom DOM Modal layers."
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "g12_c2_q1",
                    question = "Which dynamic variable specifier must be utilized if its value should never yield modification?",
                    options = listOf("let", "const", "var", "fixed"),
                    correctAnswerIndex = 1,
                    explanation = "const declares highly-secured constants that trigger crash compilation when reassigned."
                )
            )
        ),
        Lesson(
            id = "g12_c3_dom",
            grade = 12,
            chapterNumber = 3,
            chapterTitle = "Dynamic DOM scripting",
            title = "Manipulating the DOM Tree",
            iconName = "schema",
            description = "Take absolute control of your HTML page. Query nodes, update paragraphs, and build interactive UI components.",
            durationMin = 28,
            slides = listOf(
                Slide(
                    title = "What is the DOM?",
                    text = "DOM is Document Object Model. The browser parses structural HTML into a hierarchy tree of objects. JavaScript can query, add, modify, or completely delete elements.",
                    tip = "Each nested element represents a unique child node connected to parents."
                ),
                Slide(
                    title = "Finding Web Elements",
                    text = "To manipulate a tag, you must first capture it:\n- document.getElementById('id')\n- document.querySelector('.class-selector')",
                    codeExample = "const mainHeading = document.getElementById(\"logo-heading\");\nmainHeading.textContent = \"SmartTutorial Rocks!\"; // Changes text content",
                    tip = "Ensure spelling and capitalization matches your elements' declarations!"
                ),
                Slide(
                    title = "Event Listeners",
                    text = "Instead of hardcoding events inside HTML files, best-practice is to attach modern listeners.",
                    codeExample = "const promoBtn = document.querySelector(\"#promo-btn\");\npromoBtn.addEventListener(\"click\", function() {\n  alert(\"Welcome to SmartTutorial Ethiopia!\");\n});",
                    tip = "Listeners keep visual representation separate from logical scripts."
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "g12_c3_q1",
                    question = "What JavaScript method fetches elements using their unique identifier?",
                    options = listOf("document.findId()", "document.getElementById()", "document.selectNodes()", "window.getNode()"),
                    correctAnswerIndex = 1,
                    explanation = ".getElementById() targets the specific element possessing matching tag id."
                )
            )
        ),
        Lesson(
            id = "g12_c4_server",
            grade = 12,
            chapterNumber = 4,
            chapterTitle = "Server-Side Infrastructure",
            title = "Introduction to Backend & PHP",
            iconName = "dns",
            description = "Explore databases and backend programming. Connect client layouts to server-side scripts.",
            durationMin = 35,
            slides = listOf(
                Slide(
                    title = "The Backend Concept",
                    text = "Client applications run in client devices. Backend systems reside on high-power servers. Backends process business requirements, communicate safely with databases, and handle storage securely.",
                    tip = "Backend acts as the mastermind guarding core logic."
                ),
                Slide(
                    title = "PHP Basics",
                    text = "PHP is a server-side language. PHP scripts execute on servers and return static standard HTML back to client phones.",
                    codeExample = "<?php\n  \$platformName = \"SmartTutorial.com\";\n  echo \"<h1>Learn Web Dev on \$platformName</h1>\";\n?>",
                    tip = "Clients never see raw server-side script files; they only see clean processed output results!"
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "g12_c4_q1",
                    question = "PHP is a technology specialized in which layer?",
                    options = listOf("Client-side styling", "Vector canvas drawings", "Server-side operations", "User screen presentation"),
                    correctAnswerIndex = 2,
                    explanation = "PHP is Hypertext Preprocessor, a dedicated server-side language executed on hosting computers."
                )
            )
        )
    )

    val smartCourses = listOf(
        SmartTutorialCourse(
            id = "st_c1_web_bootstrap",
            title = "Full-Stack Web Development Bootcamp",
            category = "Web Development",
            description = "Go from zero experience to building modern web landing pages, databases, and servers. Includes special guidance for Grade 11 & 12 exam success.",
            rating = 4.9f,
            reviewsCount = 420,
            level = "All Levels",
            price = "Premium (ETB 2,500)",
            durationHours = 45,
            lessonsCount = 120,
            features = listOf(
                "Hands-on HTML, CSS, & Modern JavaScript",
                "Backend Servers with Node.js & secure databases",
                "Hosting, domains & publication",
                "Live doubt solving and certificates"
            )
        ),
        SmartTutorialCourse(
            id = "st_c2_python",
            title = "Python and AI Fundamentals",
            category = "Data Science & AI",
            description = "Unlock the world of automation, algorithms, and artificial intelligence. Excellent introduction for computer science aspirants.",
            rating = 4.8f,
            reviewsCount = 180,
            level = "Beginner",
            price = "Premium (ETB 1,800)",
            durationHours = 30,
            lessonsCount = 85,
            features = listOf(
                "Python syntax & core object variables",
                "Working with JSON and Rest APIs",
                "Data analysis with Pandas & NumPy",
                "Introductory AI models & prompt engineering"
            )
        ),
        SmartTutorialCourse(
            id = "st_c3_mobile",
            title = "Modern Android Dev with Jetpack Compose",
            category = "Mobile Apps",
            description = "Build high-speed, natively rendered Android applications with Kotlin, Google guidelines, and clean architectures.",
            rating = 4.95f,
            reviewsCount = 95,
            level = "Intermediate",
            price = "Premium (ETB 3,200)",
            durationHours = 40,
            lessonsCount = 95,
            features = listOf(
                "Kotlin grammar & asynchronous coroutines",
                "Declarative UI components with Jetpack Compose",
                "Room SQL local databases",
                "Publishing projects on Google Play Store"
            )
        ),
        SmartTutorialCourse(
            id = "st_c4_ict_guide",
            title = "Ethiopian National Exams Prep Guide (ICT)",
            category = "Exam Prep",
            description = "Crush Grade 11 & 12 ICT exams with SmartTutorial. Focus on Web Development theories, hardware, and networks with quick cheat sheets.",
            rating = 4.7f,
            reviewsCount = 310,
            level = "Exam Prep",
            price = "FREE (Sponsored)",
            durationHours = 12,
            lessonsCount = 30,
            features = listOf(
                "Web theory shortcuts & tables",
                "Dozens of chapter test questions",
                "Exam hacks for quick calculations",
                "Verified solutions to past boards"
            )
        )
    )

    val announcements = listOf(
        CourseAnnouncement(
            id = "ann_1",
            title = "Free Web Dev Live Study Session!",
            content = "Join our upcoming live webinar with SmartTutorial's top instructors. We will review Grade 12 CSS positioning, DOM querying, and critical exam problems.",
            date = "Oct 15, 2026",
            category = "Live Event"
        ),
        CourseAnnouncement(
            id = "ann_2",
            title = "New React JS Complete Guide Added",
            content = "For advanced student projects, we have launched a full React JS course path on SmartTutorial.com. Covers state hooks, client routes, and production deployment.",
            date = "Nov 02, 2026",
            category = "Course Launch"
        ),
        CourseAnnouncement(
            id = "ann_3",
            title = "Ethiopia Grade 11-12 Mini-Contest",
            content = "Build an outstanding landing page showcasing Ethiopian history or culture. Win SmartTutorial Premium Course access certificates!",
            date = "Dec 12, 2026",
            category = "Contest"
        )
    )

    val testimonials = listOf(
        Testimonial(
            name = "Eyob S.",
            role = "Grade 12 Student, Addis Ababa",
            quote = "SmartTutorial's interactive web design lessons helped me score 100/100 in my high school ICT web projects. It's so simple to follow!",
            rating = 5
        ),
        Testimonial(
            name = "Seble G.",
            role = "Freelancer & Developer",
            quote = "The bootcamp on smartutorial.com gave me the real-world HTML/CSS confidence I needed. I am already coding landing pages for clients.",
            rating = 5
        ),
        Testimonial(
            name = "Kidus B.",
            role = "Grade 11 Student",
            quote = "HTML was confusing to read on blackboards. Seeing actual, live color codes and slide explanations made CSS feel like magic.",
            rating = 5
        )
    )
}
