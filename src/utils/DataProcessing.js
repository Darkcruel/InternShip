
export let humanizePhrase = (rasaObject, optionalPrefix=[]) => {
  let processedObject = rasaObject;
  let appliedPrefix = "";
  if (optionalPrefix) {
    optionalPrefix.forEach(prefix => {
      if (rasaObject.substring(0, prefix.length) === prefix){
      processedObject = rasaObject.substr(prefix.length+1);
      appliedPrefix = prefix;
      }
    })
  }
  
  processedObject = processedObject.replaceAll("_", " ");
  return [processedObject, appliedPrefix];
};


export let categorizeResponse = (utterance) => {
  let slotPrefix = /^utter_ask_/;
  let properPrefix = /^utter_/;

  if (slotPrefix.test(utterance)) {
    properPrefix = slotPrefix;
  }

  return { title: utterance.replace(properPrefix, ""), forSlot: slotPrefix.test(utterance) };
};

