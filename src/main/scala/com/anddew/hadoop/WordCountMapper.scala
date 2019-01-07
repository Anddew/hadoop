package com.anddew.hadoop

import java.util.StringTokenizer

import org.apache.hadoop.io.{IntWritable, LongWritable, Text}
import org.apache.hadoop.mapreduce.Mapper

class WordCountMapper extends Mapper[LongWritable, Text, Text, IntWritable] {

  override def map(key: LongWritable, value: Text, context: Mapper[LongWritable, Text, Text, IntWritable]#Context): Unit = {
    val tokenizer = new StringTokenizer(value.toString)
    val word: Text = new Text()
    val one = new IntWritable(1)

    while (tokenizer.hasMoreElements) {
      word.set(tokenizer.nextToken())
      context.write(word, one)
    }
  }

  override def run(context: Mapper[LongWritable, Text, Text, IntWritable]#Context): Unit = {

  }

}