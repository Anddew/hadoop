package com.anddew.hadoop

import java.lang.Iterable

import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.Reducer

import scala.collection.JavaConverters._


class WordCountReducer extends Reducer[Text, IntWritable, Text, IntWritable] {

  override def reduce(key: Text, values: Iterable[IntWritable], context: Reducer[Text, IntWritable, Text, IntWritable]#Context): Unit = {
    var sum: Int = 0
    for (value <- values.asScala)
      sum += value.get()
    context.write(key, new IntWritable(sum))
  }

}