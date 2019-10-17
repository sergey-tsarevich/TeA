#Если бы наши слова собрать все воедино, то мы увидели бы свое собственное изображение.#
+1. Find X characters before Gold Value and X characters after Gold Value.
Store in 2 csv files (before and after).
+2. Find the N most frequent words from the stored files separated by '\W' regex.
The more frequent is word the more weight of this word. (For example https://www.geeksforgeeks.org/find-the-k-most-frequent-words-from-a-file/  )
3. Search the most heavy(by sum of weight of separate words) phrases and make RegEx expressions from them, e.g. spaces replace with '\s*', add common OCRs [8B]|[5S] etc.
(For example https://www.geeksforgeeks.org/word-break-problem-dp-32/  )
4. Create new Regex Annotators and add it to configuration.
  
### todos:
- search the most heavy(by sum of weight of separate words) phrases
    -+ https://medium.com/@codingfreak/recursion-practice-problems-d8c6b4fbb04e - one-word algorithms(
    -UIMA
duplicate code algorithm

News.isNegative?
#Online tools
http://sporkforge.com/text/word_count.php => N/A
https://www.online-utility.org/text/analyzer.jsp        7/10
https://prowritingaid.com/en/Analysis/WebEditor/Go?redirectToDocs=true#_=_      9/10