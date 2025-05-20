function addImgWidthStyle(html, width) {  
	if(html){
		return html.replace(/<img([^>]*?)>/gi, (match, p1) => {
		  // 提取style属性（如果存在）  
		  let styleAttr = p1.match(/style="([^"]*)"/);  
		  let existingStyle = styleAttr ? styleAttr[1] : '';  
		
		  // 构造新的style字符串，确保添加width样式  
		  let newStyle = `${existingStyle ? existingStyle + '; ' : ''}width: ${width};`;  
		
		  // 如果原始标签中没有style属性，则直接添加style="width: ..."  
		  // 否则，替换现有的style属性  
		  if (!styleAttr) {  
		    return match.replace(/>$/, ` style="${newStyle}">`);  
		  } else {  
		    return match.replace(styleAttr[0], `style="${newStyle}"`);  
		  }  
		}); 
	}
  // 使用正则表达式来查找并替换<img>标签的style属性  
 
}  

module.exports = {
	addImgWidthStyle
}
