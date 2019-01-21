package com.anddew.hadoop

import org.apache.hadoop.conf.{Configuration, Configured}
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.mapreduce.lib.input.{FileInputFormat, TextInputFormat}
import org.apache.hadoop.mapreduce.lib.output.{FileOutputFormat, TextOutputFormat}
import org.apache.hadoop.util.{Tool, ToolRunner}

class WordCountJob extends Configured with Tool {

  override def run(args: Array[String]): Int = {
    require(args.length >= 2, "Type input and output paths.")
    val inputPath = new Path(args(1))
    val outputPath = new Path(args(2))

    val fs = FileSystem.get(new Configuration)
    fs.delete(outputPath, true)

    val job: Job = Job.getInstance(fs.getConf, "Word Count")
    job.setJarByClass(getClass)

    FileInputFormat.addInputPath(job, inputPath)
    job.setInputFormatClass(classOf[TextInputFormat])

    job.setMapperClass(classOf[WordCountMapper])
    job.setReducerClass(classOf[WordCountReducer])
    job.setCombinerClass(classOf[WordCountReducer])

    FileOutputFormat.setOutputPath(job, outputPath)
    job.setOutputFormatClass(classOf[TextOutputFormat[_,_]])
    job.setOutputKeyClass(classOf[Text])
    job.setOutputValueClass(classOf[IntWritable])

    if (job.waitForCompletion(true))
      0
    else
      1
  }

}

object WordCountJob {

  def main(args: Array[String]): Unit = {
    val exitCode = ToolRunner.run(new WordCountJob, args)
    System.exit(exitCode)
  }

}