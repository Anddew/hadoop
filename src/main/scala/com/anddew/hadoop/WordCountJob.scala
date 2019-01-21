package com.anddew.hadoop

import org.apache.hadoop.conf.Configured
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.mapreduce.lib.input.{FileInputFormat, TextInputFormat}
import org.apache.hadoop.mapreduce.lib.output.{FileOutputFormat, TextOutputFormat}
import org.apache.hadoop.util.{Tool, ToolRunner}

class WordCountJob extends Configured with Tool {

  override def run(strings: Array[String]): Int = {
    require(strings.length >= 2, "Type input and output paths.")

    val job: Job = Job.getInstance(getConf, "WordCount")
    job.setJarByClass(getClass)

    FileInputFormat.addInputPath(job, new Path(strings(1)))
    job.setInputFormatClass(classOf[TextInputFormat])

    job.setMapperClass(classOf[WordCountMapper])
    job.setReducerClass(classOf[WordCountReducer])
    job.setCombinerClass(classOf[WordCountReducer])

    FileOutputFormat.setOutputPath(job, new Path(strings(2)))
    job.setOutputFormatClass(classOf[TextOutputFormat[_,_]])
    job.setOutputKeyClass(classOf[Text])
    job.setOutputValueClass(classOf[IntWritable])

    if (job.waitForCompletion(true))
      0
    else
      1
  }

}

object WordCountJob extends App {
  val exitCode = ToolRunner.run(new WordCountJob, args)
  System.exit(exitCode)
}